package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.TransformationReport;
import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XStreamAlias("eu.unifiedviews.plugins.transformer.filesfindandreplace.FilesFindAndReplaceConfig_V2")
class FilesFindAndReplaceConfig_V2 implements Configuration {

    @XStreamAlias("eu.unifiedviews.plugins.transformer.filesfindandreplace.Encoding")
    static enum Encoding {
        UTF8("UTF-8"),
        UTF16("UTF-16"),
        ISO_8859_1("ISO-8859-1"),
        WINDOWS1250("windows-1250");

        private String charset;

        Encoding(String charset) {
            this.charset = charset;
        }

        public String getCharset() {
            return charset;
        }
    }

    private static final Logger LOG
            = LoggerFactory.getLogger(FilesFindAndReplaceConfig_V2.class);

    private Map<String, String> patterns = new HashMap<>();

    private Encoding encoding = Encoding.UTF8;

    private boolean skipOnError = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        // @DataUnit.AsInput(name = "filesInput")
        // @DataUnit.AsOutput(name = "filesOutput")

        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);
    }

}
