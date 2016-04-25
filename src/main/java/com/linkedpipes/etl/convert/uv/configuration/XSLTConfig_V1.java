package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.xslt.XSLTConfig_V1")
class XSLTConfig_V1 implements Configuration {

    private String xslTemplate = "";

    private String xslTemplateFileNameShownInDialog = "";

    private boolean skipOnError = false;

    private String xsltParametersMapName = "xsltParameters";

    private String outputFileExtension = "";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        final XsltConfig_V2 config = new XsltConfig_V2();

        config.failOnError = !this.skipOnError;
        config.xsltTemplate = this.xslTemplate;
        config.xsltTemplateName = this.xslTemplateFileNameShownInDialog;
        config.outputFileExtension = this.outputFileExtension;

        config.update(pipeline, component);
    }

}
