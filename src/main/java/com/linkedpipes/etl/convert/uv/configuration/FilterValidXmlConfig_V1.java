package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.TransformationReport;
import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XStreamAlias("eu.unifiedviews.plugins.transformer.filtervalidxml.FilterValidXmlConfig_V1")
class FilterValidXmlConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(FilterValidXmlConfig_V1.class);

    private String xsdContents;

    private String xsdFileUploadLabel;

    private String xsltContents;

    private String xsltFileUploadLabel;

    private boolean failPipelineOnValidationError;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        // @DataUnit.AsInput(name = "input")
        // @DataUnit.AsOutput(name = "outputValid")
        // @DataUnit.AsOutput(name = "outputInvalid")

        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);
    }

}
