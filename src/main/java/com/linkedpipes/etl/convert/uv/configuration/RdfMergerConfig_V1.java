package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.rdfmerger.RdfMergerConfig_V1")
class RdfMergerConfig_V1 implements Configuration {

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        pipeline.renameInPort(component, "rdfInput", "InputRdf");
        pipeline.renameOutPort(component, "rdfOutput", "OutputRdf");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-singleGraphUnion");
    }

}
