package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.LinkedList;
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
@XStreamAlias("eu.unifiedviews.plugins.extractor.filesdownload.FilesDownloadConfig_V1")
class FilesDownloadConfig_V1 implements Configuration {

    @XStreamAlias("eu.unifiedviews.plugins.extractor.filesdownload.VfsFile")
    static class VfsFile {

        String uri = "";

        String username = "";

        String password = "";

        String fileName = "";

    }

    private static final Logger LOG
            = LoggerFactory.getLogger(FilesDownloadConfig_V1.class);

    List<VfsFile> vfsFiles = new LinkedList<>();

    boolean softFail = false;

    int defaultTimeout = 20000;

    boolean ignoreTlsErrors = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        if (vfsFiles.isEmpty()) {
            LOG.info("Empty");
            throw new UnsupportedOperationException("FilesDownloadConfig_V1");
        } else if (vfsFiles.size() == 1) {
            toHttpGet(pipeline, component);
        } else {
            LOG.info("Many");
            throw new UnsupportedOperationException("FilesDownloadConfig_V1");
        }

    }

    public void toHttpGet(LpPipeline pipeline, LpPipeline.Component component) {

        pipeline.renameOutPort(component, "output", "FilesOutput");

        component.setTemplate("http://localhost:8080/resources/components/e-httpGetFile");

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

        component.setLpConfiguration(st);
    }

}
