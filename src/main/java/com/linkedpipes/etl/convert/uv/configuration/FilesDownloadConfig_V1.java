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
@XStreamAlias("eu.unifiedviews.plugins.extractor.filesdownload.FilesDownloadConfig_V1")
class FilesDownloadConfig_V1 implements Configuration {

    @XStreamAlias("eu.unifiedviews.plugins.extractor.filesdownload.VfsFile")
    static class VfsFile {

        String uri = "";

        String username = "";

        String password = "";

        String fileName = "";

        VfsFile() {
        }

        VfsFile(String uri, String fileName) {
            this.uri = uri;
            this.fileName = fileName;
        }

    }

    private static final Logger LOG
            = LoggerFactory.getLogger(FilesDownloadConfig_V1.class);

    List<VfsFile> vfsFiles = new LinkedList<>();

    boolean softFail = false;

    int defaultTimeout = 20000;

    boolean ignoreTlsErrors = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        if (vfsFiles.isEmpty()) {
            LOG.error("At least one file to download must be specified.");
            throw new UnsupportedOperationException("FilesDownloadConfig_V1");
        } else if (vfsFiles.size() == 1) {
            final VfsFile file = vfsFiles.get(0);
            if (file.uri.startsWith("file://")) {
                toLocal(pipeline, component, asTemplate);
            } else {
                toHttpGet(pipeline, component, asTemplate);
            }
        } else {
            LOG.error("Multiple files to download are not suported.");
            throw new UnsupportedOperationException("FilesDownloadConfig_V1");
        }

    }

    public void toLocal(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameOutPort(component, "output", "FilesOutput");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/e-filesFromLocal/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-filesFromLocal"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-filesFromLocal#Configuration")));

        final VfsFile file = vfsFiles.get(0);
        if (!file.fileName.endsWith(file.fileName)) {
            LOG.warn("{} : Custom name of local file is not supported. Please check the pipeline.", component);
        }

        final String path = file.uri.substring("file://".length());
        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-filesFromLocal"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-filesFromLocal#path"),
                vf.createLiteral(path)));

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/e-filesFromLocal"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-filesFromLocal#pathControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

    public void toHttpGet(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameOutPort(component, "output", "FilesOutput");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/e-httpGetFile/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        final VfsFile file = vfsFiles.get(0);

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-httpGetFile"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-httpGetFile#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-httpGetFile"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-httpGetFile#fileUri"),
                vf.createLiteral(file.uri)));

        if (file.fileName == null || file.fileName.isEmpty()) {
            file.fileName = "generated_name";
        }

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-httpGetFile"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-httpGetFile#fileName"),
                vf.createLiteral(file.fileName)));

        if (softFail) {
            LOG.warn("{} : Soft fail is not supported.", component);
        }
        if (ignoreTlsErrors) {
            LOG.warn("{} : Options 'ignoreTlsErrors' is not supported.", component);
        }
        LOG.info("{} : Custom time out is not supported.", component);

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/e-httpGetFile"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-httpGetFile#fileUriControl"),
                    force));

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/e-httpGetFile"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-httpGetFile#fileNameControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
