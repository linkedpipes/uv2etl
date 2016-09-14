package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.openrdf.model.IRI;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.RDF;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.cuni.mff.xrg.uv.transformer.HtmlCssConfig_V1")
class HtmlCssConfig_V1 implements Configuration {

    @XStreamAlias("cz.cuni.mff.xrg.uv.transformer.HtmlCssConfig_V1$ActionType")
    enum ActionType {
        QUERY,
        TEXT,
        HTML,
        ATTRIBUTE,
        OUTPUT, // LITERAL
        SUBJECT,
        UNLIST,
        SUBJECT_CLASS
    }

    @XStreamAlias("cz.cuni.mff.xrg.uv.transformer.HtmlCssConfig_V1$Action")
    static class Action {

        private String name = "http://localhost/temp/";

        private ActionType type = ActionType.TEXT;

        private String actionData = "";

        private String outputName = "";

    }

    private List<Action> actions = new LinkedList<>();

    private String classAsStr = "http://unifiedviews.eu/ontology/e-htmlCss/Page";

    private String hasPredicateAsStr = "http://unifiedviews.eu/ontology/e-htmlCss/hasObject";

    private boolean sourceInformation = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {
        pipeline.renameInPort(component, "html", "InputFiles");
        pipeline.renameOutPort(component, "rdf", "OutputRdf");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-htmlCssUv/0.0.0");

        final String PREFIX = "http://plugins.linkedpipes.com/ontology/t-htmlCssUv#";
        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI resource = vf.createIRI("http://localhost/resources/configuration/t-htmlCssUv");

        st.add(vf.createStatement(resource, RDF.TYPE,
                vf.createIRI(PREFIX + "Configuration")));

        st.add(vf.createStatement(resource,
                vf.createIRI(PREFIX + "class"),
                vf.createLiteral(classAsStr)));

        st.add(vf.createStatement(resource,
                vf.createIRI(PREFIX + "predicate"),
                vf.createLiteral(hasPredicateAsStr)));

        st.add(vf.createStatement(resource,
                vf.createIRI(PREFIX + "includeSourceInformation"),
                vf.createLiteral(sourceInformation)));

        int counter = 0;
        for (Action action : actions) {
            final IRI iri = vf.createIRI(resource.stringValue()
                    + "/action_" + (++counter));

            st.add(vf.createStatement(resource,
                    vf.createIRI(PREFIX + "action"),
                    iri));

            st.add(vf.createStatement(iri,
                    vf.createIRI(PREFIX + "name"),
                    vf.createLiteral(action.name)));
            st.add(vf.createStatement(iri,
                    vf.createIRI(PREFIX + "type"),
                    vf.createLiteral(action.type.toString())));
            st.add(vf.createStatement(iri,
                    vf.createIRI(PREFIX + "data"),
                    vf.createLiteral(action.actionData)));
            st.add(vf.createStatement(iri,
                    vf.createIRI(PREFIX + "output"),
                    vf.createLiteral(action.outputName)));
        }

        component.setLpConfiguration(st);
    }

}
