package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.gzipper.GzipperConfig_V1")
class GzipperConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(GzipperConfig_V1.class);

    private boolean skipOnError = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "filesInput")
        // @DataUnit.AsOutput(name = "filesOutput")

        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);
    }

}
