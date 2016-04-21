package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.extractor.executeshellscript.ExecuteShellScriptConfig_V1")
class ExecuteShellScriptConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(ExecuteShellScriptConfig_V1.class);

    private String scriptName = "";

    private String configuration = "";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "filesInput", optional = true)

        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);
    }

}
