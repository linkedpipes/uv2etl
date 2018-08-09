package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.List;
import org.openrdf.model.IRI;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XStreamAlias("eu.unifiedviews.plugins.extractor.sparqlendpoint.SparqlEndpointConfig_V1")
class SparqlEndpointConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(SparqlEndpointConfig_V1.class);

    String endpoint = "http://localhost:8890/sparql";

    String query = "CONSTRUCT { ?s ?p ?o } WHERE { ?s ?p ?o }";

    Integer chunkSize = null;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameOutPort(component, "output", "OutputRdf");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/e-sparqlEndpoint/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-sparqlEndpoint"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-sparqlEndpoint#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-sparqlEndpoint"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-sparqlEndpoint#endpoint"),
                vf.createLiteral(endpoint)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-sparqlEndpoint"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-sparqlEndpoint#query"),
                vf.createLiteral(query)));

        if (chunkSize != null) {
            LOG.warn("{} : ChunkSize option is not supported.", component);
        }

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/e-sparqlEndpoint"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-sparqlEndpoint#endpointControl"),
                    force));

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/e-sparqlEndpoint"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-sparqlEndpoint#queryControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
