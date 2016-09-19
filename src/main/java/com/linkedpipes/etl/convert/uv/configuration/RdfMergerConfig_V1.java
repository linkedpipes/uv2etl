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
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameInPort(component, "rdfInput", "InputRdf");
        pipeline.renameOutPort(component, "rdfOutput", "OutputRdf");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-singleGraphUnion/0.0.0");
    }

}
