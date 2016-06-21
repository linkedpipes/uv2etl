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
@XStreamAlias("eu.unifiedviews.plugins.loader.filesupload.FilesUploadConfig_V1")
class FilesUploadConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(FilesUploadConfig_V1.class);

    String uri = "ftps://server:21/path/";

    String username = "";

    String password = "";

    boolean softFail = false;

    boolean moveFiles = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        if (pipeline.removeInConnections(component, "config")) {
            LOG.warn("{} : 'config' connection ignored.", component);
        }

        if (pipeline.removeOutConnections(component, "output")) {
            LOG.warn("{} : 'output' connection ignored.", component);
        }

        if (softFail) {
            LOG.warn("{} : Does not support soft failure.", component);
        }

        if (moveFiles) {
            LOG.warn("{} : Does not support move files.", component);
        }

        // sftp -> SCP
        if (uri.startsWith("file")) {
            // Replace with local file upload.
            toLocalUpload(pipeline, component);
        } else {
            LOG.error("{} : Component ignored.", component);
            pipeline.removeComponent(component);
        }

    }

    private void toLocalUpload(LpPipeline pipeline,
            LpPipeline.Component component) {

        pipeline.renameInPort(component, "input", "FilesInput");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/l-filesToLocal");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/l-filesToLocal"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-filesToLocal#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/l-filesToLocal"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-filesToLocal#path"),
                vf.createLiteral(uri.replace("file://", ""))));

        component.setLpConfiguration(st);
    }

}
