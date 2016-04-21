package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.xslt.XSLTConfig_V1")
class XSLTConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(XSLTConfig_V1.class);

    private String xslTemplate = "";

    private String xslTemplateFileNameShownInDialog = "";

    private boolean skipOnError = false;

    private String xsltParametersMapName = "xsltParameters";

    private String outputFileExtension = "";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "config", description = "Optional input for RDF configuration.", optional = true)
        // @DataUnit.AsInput(name = "filesInput", description = "Files to process with XSLT processor.")
        // @DataUnit.AsOutput(name = "filesOutput", description = "Processed files. Only file contet has been changed.")
        
        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);
    }

}
