package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.loader.filestolocalfs.FilesToLocalFSConfig_V1")
public class FilesToLocalFSConfig_V1 implements Configuration {

    private String destination = "/tmp";

    private boolean moveFiles = false;

    private boolean replaceExisting = false;

    private boolean skipOnError = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {
        FilesUploadConfig_V1 config = new FilesUploadConfig_V1();

        config.password = "";
        config.softFail = skipOnError;
        config.moveFiles = moveFiles;
        config.username = "";

        final StringBuilder uriStr = new StringBuilder();
        uriStr.append("file:/");
        if (!destination.startsWith("/")) {
            uriStr.append("/");
        }
        uriStr.append(destination);

        config.uri = uriStr.toString();

        config.update(pipeline, component, asTemplate);
    }

}
