package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.cuni.mff.xrg.uv.transformer.graphmerge.GraphMergeConfig_V1")
class GraphMergeConfig_V1 implements Configuration {

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        pipeline.renameInPort(component, "input", "InputRdf");
        pipeline.renameOutPort(component, "output", "OutputRdf");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-graphMerger");
    }

}
