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
@XStreamAlias("eu.unifiedviews.plugins.loader.filestovirtuoso.VirtuosoLoaderConfig_V1")
class VirtuosoLoaderConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(VirtuosoLoaderConfig_V1.class);

    private String virtuosoUrl = "";

    private String username = "";

    private String password = "";

    private boolean clearDestinationGraph = false;

    private String loadDirectoryPath = "";

    private boolean includeSubdirectories = true;

    private String loadFilePattern = "%";

    private String targetContext = "";

    private long statusUpdateInterval = 60L;

    private int threadCount = 1;

    private boolean skipOnError = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        if (pipeline.removeInConnections(component, "config")) {
            LOG.warn("{} : 'config' connection ignored.", component);
        }

        if (pipeline.removeOutConnections(component, "rdfOutput")) {
            LOG.warn("{} : 'rdfOutput' connection ignored.", component);
        }

        if (includeSubdirectories) {
            LOG.warn("{} : Support of subdirectories is not available.", component);
        }

        if (threadCount > 1) {
            LOG.info("{} : Multiple threads are not supported.", component);
        }

        if (skipOnError) {
            LOG.warn("{} : Does not support 'skipOnError'.", component);
        }

        component.setTemplate("http://etl.linkedpipes.com/resources/components/x-virtuoso/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#uri"),
                vf.createLiteral(virtuosoUrl)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#username"),
                vf.createLiteral(username)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#password"),
                vf.createLiteral(password)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#clearGraph"),
                vf.createLiteral(clearDestinationGraph)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#directory"),
                vf.createLiteral(loadDirectoryPath)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#fileName"),
                vf.createLiteral(loadFilePattern)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#graph"),
                vf.createLiteral(targetContext)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#updateInterval"),
                vf.createLiteral(statusUpdateInterval)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/x-virtuoso"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/x-virtuoso#clearSqlLoadTable"),
                vf.createLiteral(true)));

        component.setLpConfiguration(st);

    }

}
