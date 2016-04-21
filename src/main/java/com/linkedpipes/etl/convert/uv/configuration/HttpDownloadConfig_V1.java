package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.net.URL;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.extractor.httpdownload.HttpDownloadConfig_V1")
class HttpDownloadConfig_V1 implements Configuration {

    URL URL = null;

    String target = "/file";

    int retryCount = -1;

    int retryDelay = 1000;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // -> FilesDownloadConfig_V1
        final FilesDownloadConfig_V1 config = new FilesDownloadConfig_V1();
        final FilesDownloadConfig_V1.VfsFile vfsFile
                = new FilesDownloadConfig_V1.VfsFile();

        if (URL != null) {
            vfsFile.uri = URL.toString();
        } else {
            vfsFile.uri = "";
        }
        vfsFile.fileName = target;
        config.vfsFiles.add(vfsFile);

        config.update(pipeline, component);
    }

}
