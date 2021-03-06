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

@XStreamAlias("cz.cuni.mff.xrg.uv.transformer.sparql.construct.SparqlConstructConfig_V1")
class SparqlConstructConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(SparqlConstructConfig_V1.class);

    String query = "CONSTRUCT {?s ?p ?o} WHERE {?s ?p ?o}";

    boolean perGraph = true;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameInPort(component, "input", "InputRdf");
        pipeline.renameOutPort(component, "output", "OutputRdf");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-sparqlConstruct/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-sparqlConstruct"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlConstruct#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-sparqlConstruct"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlConstruct#query"),
                vf.createLiteral(query)));

        if (perGraph) {
            LOG.warn("{} : Per graph mode ignored.", component);
        }

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/t-sparqlConstruct"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlConstruct#queryControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
