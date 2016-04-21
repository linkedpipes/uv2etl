package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.rdfmerger.RdfMergerConfig_V1")
class RdfMergerConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(RdfMergerConfig_V1.class);

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        LOG.info("{} : Using merger instead of syntantic-sugar component. "
                + "This may cause some performance drop.", component);

        pipeline.renameInPort(component, "rdfInput", "InputRdf");
        pipeline.renameOutPort(component, "rdfOutput", "OutputRdf");

        component.setTemplate("http://localhost:8080/resources/components/t-graphMerger");
    }

}
