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

@XStreamAlias("eu.unifiedviews.plugins.extractor.filestoscp.FilesToScpConfig_V1")
class FilesToScpConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(FilesToScpConfig_V1.class);

    private String hostname = "";

    private Integer port = 22;

    private String username = "";

    private String password = "";

    private String destination = "/";

    private boolean softFail = true;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        if (softFail) {
            LOG.warn("{} : Does not support soft failure.", component);
        }

        pipeline.renameInPort(component, "input", "FilesInput");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/l-filesToScp/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/l-filesToScp"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-filesToScp#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/l-filesToScp"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-filesToScp#host"),
                vf.createLiteral(hostname)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/l-filesToScp"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-filesToScp#port"),
                vf.createLiteral(port)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/l-filesToScp"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-filesToScp#directory"),
                vf.createLiteral(destination)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/l-filesToScp"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-filesToScp#createDirectory"),
                vf.createLiteral(true)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/l-filesToScp"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-filesToScp#userName"),
                vf.createLiteral(username)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/l-filesToScp"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-filesToScp#password"),
                vf.createLiteral(password)));

        component.setLpConfiguration(st);
    }

}
