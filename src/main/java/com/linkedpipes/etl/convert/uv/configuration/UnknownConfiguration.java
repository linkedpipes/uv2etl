package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
public class UnknownConfiguration implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(UnknownConfiguration.class);

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {
        LOG.error("{} : Component ignored!", component);
        pipeline.removeComponent(component);
    }

}
