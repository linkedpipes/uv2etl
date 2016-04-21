package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.extractor.filestoscp.FilesToScpConfig_V1")
class FilesToScpConfig_V1 implements Configuration {

    private String hostname = "";

    private Integer port = 22;

    private String username = "";

    private String password = "";

    private String destination = "/";

    private boolean softFail = true;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        final FilesUploadConfig_V1 config = new FilesUploadConfig_V1();
        config.password = password;
        config.softFail = softFail;
        config.username = username;

        final StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("sftp://");
        uriBuilder.append(hostname);
        uriBuilder.append(":");
        uriBuilder.append(port);
        uriBuilder.append(destination);
        config.uri = uriBuilder.toString();

        config.update(pipeline, component);
    }

}
