package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;

/**
 * Represent a UV component and its configuration.
 */
public interface Configuration {

    /**
     *
     * @param pipeline The LP pipeline.
     * @param component The LP component, owner of the configuration.
     * @param asTemplate True if the configuration will be used for a template.
     */
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate);

}
