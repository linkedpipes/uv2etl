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

@XStreamAlias("eu.unifiedviews.plugins.trandformer.sparqlselect.SparqlSelectConfig")
class SparqlSelectConfig implements Configuration {

    private String targetPath = "/out.csv";

    private String query = "SELECT ?s ?p ?o WHERE { ?s ?p ?o }";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameInPort(component, "input", "InputRdf");
        pipeline.renameOutPort(component, "output", "OutputFiles");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-sparqlSelect/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI configuration = vf.createIRI("http://localhost/resources/configuration/t-sparqlSelect");

        st.add(vf.createStatement(configuration,
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlSelect#Configuration")));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlSelect#query"),
                vf.createLiteral(this.query)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlSelect#fileName"),
                vf.createLiteral(this.targetPath)));

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(configuration,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlSelect#queryControl"),
                    force));

            st.add(vf.createStatement(configuration,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlSelect#fileNameControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
