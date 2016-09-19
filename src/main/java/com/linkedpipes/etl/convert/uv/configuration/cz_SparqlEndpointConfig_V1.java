package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.List;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.RDF;

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

        component.setLpConfiguration(st);

    }

}
