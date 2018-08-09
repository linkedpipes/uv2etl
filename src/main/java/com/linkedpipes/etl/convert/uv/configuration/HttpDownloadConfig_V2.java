package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XStreamAlias("cz.cuni.mff.xrg.uv.extractor.httpdownload.HttpDownloadConfig_V2")
public class HttpDownloadConfig_V2 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(HttpDownloadConfig_V2.class);

    @XStreamAlias("cz.cuni.mff.xrg.uv.extractor.httpdownload.DownloadInfo_V1")
    public class DownloadInfo_V1 {

        private String uri;

        private String virtualPath;

    }

    private List<DownloadInfo_V1> toDownload = new LinkedList<>();

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {
        if (toDownload.isEmpty() && pipeline.inConnections(component, "config")) {
            runtimeConfiguration(pipeline, component, asTemplate);
        } else {
            staticConfiguration(pipeline, component, asTemplate);
        }
    }

    private void runtimeConfiguration(LpPipeline pipeline,
            LpPipeline.Component component, boolean asTemplate) {
        pipeline.renameInPort(component, "config", "Configuration");
        pipeline.renameOutPort(component, "files", "FilesOutput");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/e-httpGetFiles/0.0.0");

        component.setLabel("[UPDATE INPUT]\n" + component.getLabel());

        // Used runtime vocabulary:
        // http://unifiedviews.eu/ontology/dpu/filesDownload/
        //  Config
        //  hasFile
        //  -> File
        //      uri
        //      fileName

        LOG.warn("{} : Please update configuration of the source to "
                + "match new vocabulary.", component);

        component.setLpConfiguration(Collections.EMPTY_LIST);
    }

    private void staticConfiguration(LpPipeline pipeline,
            LpPipeline.Component component, boolean asTemplate) {
        if (pipeline.removeInConnections(component, "config")) {
            LOG.warn("{} : Runtime configuration is not supported!", component);
        }

        // Update ports to match FilesDownloadConfig_V1
        pipeline.renameOutPort(component, "files", "output");

        final FilesDownloadConfig_V1 config = new FilesDownloadConfig_V1();

        for (DownloadInfo_V1 info : toDownload) {
            config.vfsFiles.add(new FilesDownloadConfig_V1.VfsFile(
                    info.uri, info.virtualPath));
        }

        config.update(pipeline, component, asTemplate);
    }

}
