package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;

/**
 * Represent a UV component and its configuration.
 *
 * @author Petr Škoda
 */
public interface Configuration {

    public void update(LpPipeline pipeline, LpPipeline.Component component);

}
