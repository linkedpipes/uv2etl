package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.trandformer.sparqlselect.SparqlSelectConfig")
class SparqlSelectConfig implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(SparqlSelectConfig.class);

    private String targetPath = "/out.csv";

    private String query = "SELECT ?s ?p ?o WHERE { ?s ?p ?o }";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "input")
        // @DataUnit.AsOutput(name = "output")

        // @Component.InputPort(id = "InputRdf")
        // @Component.OutputPort(id = "OutputFiles")

        // http://plugins.linkedpipes.com/ontology/t-sparqlSelect#
        // query
        // fileName

        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);
    }

}
