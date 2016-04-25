package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.rdfvalidator.RdfValidatorConfig_V2")
class RdfValidatorConfig_V2 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(RdfValidatorConfig_V2.class);

    private boolean failExecution = false;

    private String query = "ASK {?s ?p ?p}";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "rdfInput")
        // @DataUnit.AsOutput(name = "rdfCopyOfInput", optional = true)

        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);
    }

}