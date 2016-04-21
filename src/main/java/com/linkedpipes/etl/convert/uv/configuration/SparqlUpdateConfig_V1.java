package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.List;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.cuni.mff.xrg.uv.transformer.sparql.update.SparqlUpdateConfig_V1")
class SparqlUpdateConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(SparqlUpdateConfig_V1.class);

    String query = "INSERT {?s ?p ?o} WHERE {?s ?p ?o}";

    boolean perGraph = true;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        pipeline.renameInPort(component, "input", "InputRdf");
        pipeline.renameOutPort(component, "output", "OutputRdf");

        component.setTemplate("http://localhost:8080/resources/components/t-sparqlUpdate");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-sparqlUpdate"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlUpdate#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-sparqlUpdate"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlUpdate#query"),
                vf.createLiteral(query)));

        if (perGraph) {
            LOG.warn("{} : Per graph mode ignored.", component);
        }

        component.setLpConfiguration(st);
    }

}
