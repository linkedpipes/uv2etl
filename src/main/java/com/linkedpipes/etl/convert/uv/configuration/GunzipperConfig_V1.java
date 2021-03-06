package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.TransformationReport;
import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XStreamAlias("eu.unifiedviews.plugins.transformer.gunzipper.GunzipperConfig_V1")
class GunzipperConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(GunzipperConfig_V1.class);

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
