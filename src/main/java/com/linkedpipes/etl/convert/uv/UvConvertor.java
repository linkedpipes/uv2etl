package com.linkedpipes.etl.convert.uv;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.linkedpipes.etl.convert.uv.pipeline.UvPipeline;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.JSONLDMode;
import org.openrdf.rio.helpers.JSONLDSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
public class UvConvertor {

    private static final Logger LOG = LoggerFactory.getLogger(UvConvertor.class);

    private static class IgnoreMissingMapper extends MapperWrapper {

        IgnoreMissingMapper(Mapper wrapped) {
            super(wrapped);
        }

        @Override
        public boolean shouldSerializeMember(Class definedIn, String fieldName) {
            if (definedIn == Object.class) {
                // Skip missing properties.
                return false;
            }
            return super.shouldSerializeMember(definedIn, fieldName);
        }

    }

    private static UvPipeline readUvPIpeline(File path) throws IOException {
        final XStream xstream = new XStream(new DomDriver("UTF-8")) {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new IgnoreMissingMapper(next);
            }
        };
        xstream.processAnnotations(UvPipeline.class);
        //
        final ZipFile zipFile = new ZipFile(path);
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()){
            final ZipEntry entry = entries.nextElement();
            if (entry.getName().equals("pipeline.xml")) {
                try (InputStream stream = zipFile.getInputStream(entry)) {
                    return (UvPipeline)xstream.fromXML(stream);
                }
            }
        }
        throw new RuntimeException("Missing definition file.");
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: UvConvertor "
                    + "-i {UV_PIPELINE_ZIP} "
                    + "-o {LP_PIPELINE_JSON}");
            return;
        }
        String inputPath = null;
        String outputPath = null;
        int index = 0;
        while(index < args.length) {
            if (args[index].equals("-i")) {
                if (index + 1 >= args.length) {
                    System.out.println("Missing argument for -i.");
                    return;
                }
                index++;
                inputPath = args[index];
            } else if (args[index].equals("-o")) {
                if (index + 1 >= args.length) {
                    System.out.println("Missing argument for -o.");
                    return;
                }
                index++;
                outputPath = args[index];
            } else {
                System.out.println("Invalid argument: " + args[index]);
                return;
            }
            index++;
        }
        if (inputPath == null || outputPath == null) {
            System.out.println("Usage: UvConvertor "
                    + "-i {UV_PIPELINE_ZIP} "
                    + "-o {LP_PIPELINE_JSON}");
            return;
        }
        //
        final File file = new File(inputPath);
        //
        final UvPipeline uv = readUvPIpeline(file);
        final LpPipeline lp = LpPipeline.create(uv);
        //
        try (OutputStream stream = new FileOutputStream(outputPath)) {
            final RDFWriter writer = Rio.createWriter(RDFFormat.JSONLD, stream);
            writer.set(JSONLDSettings.JSONLD_MODE, JSONLDMode.FLATTEN);
            writer.startRDF();
            for (Statement st : lp.serialize()) {
                writer.handleStatement(st);
            }
            writer.endRDF();
        } catch (IOException ex) {
            LOG.error("Can't write pipeline to file.", ex);
        }

    }

}
