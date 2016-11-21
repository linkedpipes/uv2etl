package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.cuni.mff.xrg.uv.transformer.sparql.linker.SparqlLinkerConfig_V1")
public class SparqlLinkerConfig_V1 implements Configuration {

    private static final Logger LOG =
            LoggerFactory.getLogger(SparqlLinkerConfig_V1.class);

    private String query = "CONSTRUCT {?s ?p ?o} WHERE {?s ?p ?o}";

    private boolean perGraph = true;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component, boolean asTemplate) {

        pipeline.renameInPort(component, "perGraph-Input", "input");
        pipeline.renameInPort(component, "reference-Input", "input");
        pipeline.renameOutPort(component, "output", "output");

        LOG.warn("{} : Linker converted into SparqlConstruct.", component);

        final SparqlConstructConfig_V1 config = new SparqlConstructConfig_V1();
        config.perGraph = perGraph;
        config.query = query;
        config.update(pipeline, component, asTemplate);
    }

}
