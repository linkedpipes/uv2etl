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
@XStreamAlias("cz.cuni.mff.xrg.uv.extractor.ftp.FtpConfig_V1")
public class FtpConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(FtpConfig_V1.class);

    @XStreamAlias("cz.cuni.mff.xrg.uv.extractor.ftp.FtpConfig_V1$DownloadInfo_V1")
    public class DownloadInfo_V1 {

        private String uri;

        private String virtualPath;

    }

    private List<DownloadInfo_V1> toDownload = new LinkedList<>();

    private boolean usePassiveMode = true;

    private boolean useBinaryMode = false;

    private int keepAliveControl = 0;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        if (!toDownload.isEmpty()) {
            LOG.warn("User given input to FTP extractor is not supported and was ignored.");
            component.setLabel("[INPUT IGNORED]\n" + component.getLabel());
            for (DownloadInfo_V1 entry : toDownload) {
                LOG.info("\tignored entry: {} -> {}", entry.uri, entry.virtualPath);
            }
        }

        if (pipeline.inConnections(component, "config")) {
            LOG.warn("Update input to match new vocabulary.");
            component.setLabel("[UPDATE INPUT]\n" + component.getLabel());
        }

        pipeline.renameInPort(component, "config", "Configuration");
        pipeline.renameOutPort(component, "files", "FilesOutput");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-ftpFiles/0.0.0");

        final String PREFIX = "http://plugins.linkedpipes.com/ontology/t-ftpFiles#";
        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI resource = vf.createIRI("http://localhost/resources/configuration/t-ftpFiles");

        st.add(vf.createStatement(resource, RDF.TYPE,
                vf.createIRI(PREFIX + "Configuration")));

        st.add(vf.createStatement(resource,
                vf.createIRI(PREFIX + "passiveMode"),
                vf.createLiteral(usePassiveMode)));

        st.add(vf.createStatement(resource,
                vf.createIRI(PREFIX + "binaryMode"),
                vf.createLiteral(useBinaryMode)));

        st.add(vf.createStatement(resource,
                vf.createIRI(PREFIX + "keepAliveControl"),
                vf.createLiteral(keepAliveControl)));

        component.setLpConfiguration(st);
    }

}
