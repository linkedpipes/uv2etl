package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.loader.filestovirtuoso.VirtuosoLoaderConfig_V2")
class VirtuosoLoaderConfig_V2 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(VirtuosoLoaderConfig_V2.class);

    private String virtuosoUrl = "jdbc:virtuoso://localhost:1111/charset=UTF-8/";

    private String username = "dba";

    private String password = "dba";

    private boolean clearDestinationGraph = false;

    private String loadDirectoryPath = "";

    private boolean includeSubdirectories = true;

    private String loadFilePattern = "%";

    private String targetContext = "";

    private long statusUpdateInterval = 60L;

    private int threadCount = 1;

    private boolean skipOnError = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "config", optional = true)
        // @DataUnit.AsOutput(name = "rdfOutput", optional = true)

        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);
    }

}
