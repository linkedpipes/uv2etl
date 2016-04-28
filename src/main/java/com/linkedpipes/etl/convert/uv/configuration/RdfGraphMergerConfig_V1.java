package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.rdfgraphmerger.RdfGraphMergerConfig_V1")
class RdfGraphMergerConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(RdfGraphMergerConfig_V1.class);

    URI virtualGraph;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        if (virtualGraph != null) {
            LOG.info("{} : Graph name ignored.", component);
        }

        pipeline.renameInPort(component, "input", "InputRdf");
        pipeline.renameOutPort(component, "output", "OutputRdf");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-singleGraphUnion");
    }

}
