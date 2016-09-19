package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.cuni.mff.xrg.uv.extractor.sparqlendpoint.SparqlEndpointConfig_V1")
class cz_SparqlEndpointConfig_V1 implements Configuration {

    String endpoint = "http://localhost:8890/sparql";

    String query = "CONSTRUCT { ?s ?p ?o } WHERE { ?s ?p ?o }";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        final SparqlEndpointConfig_V1 config = new SparqlEndpointConfig_V1();
        config.endpoint = endpoint;
        config.query = query;

        config.update(pipeline, component, asTemplate);
    }

}
