package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.TransformationReport;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationLoader {

    private static final Logger LOG
            = LoggerFactory.getLogger(ConfigurationLoader.class);

    /**
     * Ignore missing fields.
     */
    private static class IgnoreMissingMapper extends MapperWrapper {

        IgnoreMissingMapper(Mapper wrapped) {
            super(wrapped);
        }

        @Override
        public boolean shouldSerializeMember(Class definedIn, String fieldName) {
            if (definedIn == Object.class) {
                LOG.debug("Ignored missing: {} in {}", fieldName, definedIn);
                return false;
            }
            return super.shouldSerializeMember(definedIn, fieldName);
        }

        @Override
        public Class realClass(String elementName) {
            try {
                Class clazz = super.realClass(elementName);
                return clazz;
            } catch (CannotResolveClassException ex) {
                if (elementName.contains(".")) {
                    // There might be names of "properties" ? So we require
                    // dot to be presented (in a package name). Thus
                    // we print only names of classes.
                    LOG.error("Unknown class detected: {}", elementName);
                }
                TransformationReport.getInstance()
                        .unknownConfiguration(elementName);
                return UnknownConfiguration.class;
            }
        }

    }

    private final XStream xstream;

    public ConfigurationLoader() {
        xstream = new XStream(new DomDriver("UTF-8")) {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new IgnoreMissingMapper(next);
            }
        };
        //
        xstream.processAnnotations(MasterConfigObject.class);
        //
        xstream.processAnnotations(DistributionMetadataConfig_V1.class);
        xstream.processAnnotations(DeleteDirectory_V1.class);
        xstream.processAnnotations(ExecuteShellScriptConfig_V1.class);
        xstream.processAnnotations(ExecuteShellScriptConfig_V1.class);
        xstream.processAnnotations(FilesDownloadConfig_V1.class);
        xstream.processAnnotations(FilesFilterConfig_V1.class);
        xstream.processAnnotations(FilesFindAndReplaceConfig_V2.class);
        xstream.processAnnotations(FilesMergerConfig_V1.class);
        xstream.processAnnotations(FilesToLocalFSConfig_V1.class);
        xstream.processAnnotations(FilesToParliamentConfig_V1.class);
        xstream.processAnnotations(FilesToRDFConfig_V1.class);
        xstream.processAnnotations(FilesUploadConfig_V1.class);
        xstream.processAnnotations(FilterValidXmlConfig_V1.class);
        xstream.processAnnotations(FtpConfig_V1.class);
        xstream.processAnnotations(GunzipperConfig_V1.class);
        xstream.processAnnotations(GzipperConfig_V1.class);
        xstream.processAnnotations(HtmlCssConfig_V1.class);
        xstream.processAnnotations(MetadataConfig_V1.class);
        xstream.processAnnotations(RdfGraphMergerConfig_V1.class);
        xstream.processAnnotations(RdfMergerConfig_V1.class);
        xstream.processAnnotations(RdfToVirtuosoConfig_V1.class);
        xstream.processAnnotations(RdfValidatorConfig_V2.class);
        xstream.processAnnotations(RenamerConfig_V1.class);
        xstream.processAnnotations(SilkLinkerConfig_V1.class);
        xstream.processAnnotations(SparqlConstructConfig_V1.class);
        xstream.processAnnotations(SparqlEndpointConfig_V1.class);
        xstream.processAnnotations(SparqlLinkerConfig_V1.class);
        xstream.processAnnotations(SparqlSelectConfig.class);
        xstream.processAnnotations(SparqlUpdateConfig_V1.class);
        xstream.processAnnotations(TabularConfig_V2.class);
        xstream.processAnnotations(TextHolderConfig_V1.class);
        xstream.processAnnotations(UnZipperConfig_V1.class);
        xstream.processAnnotations(VirtuosoLoaderConfig_V2.class);
        xstream.processAnnotations(XSLTConfig_V1.class);
        xstream.processAnnotations(ZipperConfig_V1.class);
        xstream.processAnnotations(RdfToFilesConfig_V2.class);
        xstream.processAnnotations(VirtuosoLoaderConfig_V1.class);
        xstream.processAnnotations(cz_SparqlEndpointConfig_V1.class);
        xstream.processAnnotations(Xls2csvConfig_V1.class);
        xstream.processAnnotations(GraphMergeConfig_V1.class);
        xstream.processAnnotations(FilesFromLocalConfig_V1.class);
        xstream.processAnnotations(HttpDownloadConfig_V1.class);
        xstream.processAnnotations(HttpDownloadConfig_V2.class);
        xstream.processAnnotations(CKANLoaderConfig.class);
        xstream.processAnnotations(CKANLoaderConfig_V3.class);
        xstream.processAnnotations(FilesToScpConfig_V1.class);
        xstream.processAnnotations(RdfToFilesConfig_V1.class);
        xstream.processAnnotations(DatasetMetadataConfig_V1.class);
        xstream.processAnnotations(cz_DistributionMetadataConfig_V1.class);
        xstream.processAnnotations(RdfNotEmptyConfig_V1.class);
        xstream.processAnnotations(RdfStatementParserConfig_V2.class);
        xstream.processAnnotations(SPARQLConfig_V1.class);
        xstream.processAnnotations(XsltConfig_V2.class);
        xstream.processAnnotations(ModifyDateConfig_V1.class);
    }

    public Configuration loadConfiguration(String configuration) {
        return loadConfiguration(xstream, configuration);
    }

    private static Configuration loadConfiguration(XStream xstream,
            String configAsString) {
        if (configAsString == null) {
            // Missing configuration
            return null;
        }
        // Update string - based on the configuration migration.
        configAsString = transformString(configAsString);
        //
        while (true) {
            final Object loadedObject
                    = deserializeStream(xstream, configAsString);
            if (loadedObject instanceof MasterConfigObject) {
                final MasterConfigObject masterConfig
                        = (MasterConfigObject) loadedObject;
                return deserializeStream(xstream,
                        masterConfig.getConfigurations().get("dpu_config"));
            } else {
                //
                throw new RuntimeException("Unknown configuration structure.");
            }
        }
    }

    private static <T> T deserializeStream(XStream xstream, String string) {
        final byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
        try (ObjectInputStream objIn = xstream.createObjectInputStream(
                new ByteArrayInputStream(bytes))) {
            return (T) objIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException("Can't load configuration.", ex);
        }
    }

    private static String transformString(String config) {
        // Fast initial check.
        if (!config.contains("<ConfigurationVersion>")) {
            return config;
        }
        final Pattern pattern = Pattern.compile("^<object-stream>\\s*<ConfigurationVersion>.*"
                + "<className>(?<class>[^<]*)</className>\\s*</ConfigurationVersion>\\s*"
                + "<Configuration>(?<config>.+)</Configuration>\\s*</object-stream>\\s*$",
                Pattern.DOTALL);

        final Matcher matcher = pattern.matcher(config);
        if (!matcher.matches()) {
            LOG.warn("Configuration does not match the pattern, but pass first test!");
            return config;
        }
        String className = matcher.group("class").replaceAll("_", "__");
        if (className.isEmpty()) {
            throw new RuntimeException("Missing class name!");
        }

        String originalConfiguration = matcher.group("config");
        originalConfiguration = originalConfiguration.
                replaceAll("&", "&amp;").
                replaceAll("<", "&lt;").
                replaceAll(">", "&gt;");

        // Create new configuration.
        final StringBuilder newConfiguration = new StringBuilder();
        newConfiguration.append(""
                + "<object-stream>\n"
                + "  <MasterConfigObject>\n"
                + "    <configurations>\n"
                + "      <entry>\n"
                + "        <string>dpu_config</string>\n"
                + "        <string>&lt;object-stream&gt;"
                + "&lt;");
        newConfiguration.append(className);
        newConfiguration.append("&gt;");
        newConfiguration.append(originalConfiguration);
        newConfiguration.append("&lt;/");
        newConfiguration.append(className);
        newConfiguration.append("&gt;"
                + "&lt;/object-stream&gt;</string>\n"
                + "      </entry>\n"
                + "    </configurations>\n"
                + "  </MasterConfigObject>\n"
                + "</object-stream>");

        return newConfiguration.toString();
    }

}
