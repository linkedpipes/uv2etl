/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.rdfstatementparser.RdfStatementParserConfig_V2")
class RdfStatementParserConfig_V2 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(RdfStatementParserConfig_V2.class);

    @XStreamAlias("eu.unifiedviews.plugins.transformer.rdfstatementparser.ActionType_V1")
    public static enum ActionType_V1 {
        CreateTriple,
        RegExp;
    }

    @XStreamAlias("eu.unifiedviews.plugins.transformer.rdfstatementparser.RdfStatementParserConfig_V2$ActionInfo")
    public static class ActionInfo {

        /**
         * Group name.
         */
        private String name;

        /**
         * Type of action with given group;
         */
        private ActionType_V1 actionType;

        /**
         * Based on {@link #actionType} contains regular expression to use or
         * predicate to for triple.
         * If regular expression is used then named groups should be used.
         */
        private String actionData;

    }

    /**
     * Map of actions. Group name and respective action.
     */
    private List<ActionInfo> actions = new LinkedList<>();

    /**
     * Query used to get values.
     */
    private String selectQuery = "SELECT * WHERE {?subject ?p ?o}";

    /**
     * If true then also labels/tags are transfered with values.
     */
    private boolean transferLabels = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {
        // input -> InputRdf
        // output -> OutputRdf

        // http://plugins.linkedpipes.com/ontology/t-valueParser#
        // regexp
        // source
        // binding -> Binding : group target
        pipeline.renameInPort(component, "input", "InputRdf");
        pipeline.renameInPort(component, "output", "OutputRdf");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-valueParser/0.0.0");

        final String PREFIX
                = "http://plugins.linkedpipes.com/ontology/t-valueParser#";
        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI resource = vf.createIRI(
                "http://localhost/resources/configuration/t-valueParser");

        st.add(vf.createStatement(resource, RDF.TYPE,
                vf.createIRI(PREFIX + "Configuration")));

        st.add(vf.createStatement(resource,
                vf.createIRI(PREFIX + "preserveMetadata"),
                vf.createLiteral(transferLabels)));

        component.setLpConfiguration(st);

        // We can migrate in very specific
        if (actions.size() == 2) {
            String regexp = null;
            String group = null;
            String predicate = null;
            for (ActionInfo action : actions) {
                switch (action.actionType) {
                    case CreateTriple:
                        regexp = action.actionData;
                        break;
                    case RegExp:
                        group = action.name;
                        predicate = action.actionData;
                        break;

                }
            }
            if (regexp != null) {
                st.add(vf.createStatement(resource,
                        vf.createIRI(PREFIX + "regexp"),
                        vf.createLiteral(regexp)));
            }
            if (predicate != null) {
                // Create binding.
                final IRI binding = vf.createIRI(
                        "http://localhost/resources/configuration/t-valueParser/binding");

                st.add(vf.createStatement(binding, RDF.TYPE,
                        vf.createIRI(PREFIX + "Binding")));

                st.add(vf.createStatement(resource,
                        vf.createIRI(PREFIX + "binding"),
                        binding));

                st.add(vf.createStatement(binding,
                        vf.createIRI(PREFIX + "group"),
                        vf.createLiteral(group)));

                st.add(vf.createStatement(binding,
                        vf.createIRI(PREFIX + "target"),
                        vf.createLiteral(predicate)));
            }
        }

        LOG.warn("{} : Check configuration.", component);
        LOG.info("{} : SPARQL:\n {}", component, selectQuery);
        component.setLabel("[CHECK]\n" + component.getLabel());

    }

}
