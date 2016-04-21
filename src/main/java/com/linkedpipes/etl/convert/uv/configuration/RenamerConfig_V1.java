package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.filesrenamer.RenamerConfig_V1")
class RenamerConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(RenamerConfig_V1.class);

    private String pattern = "xml";

    private String replaceText = "ttl";

    /**
     * If true the replace
     */
    private boolean useAdvanceReplace = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "input")
        // @DataUnit.AsOutput(name = "output")

        if (useAdvanceReplace) {
            LOG.error("{} : 'useAdvanceReplace' option is not supported.",
                    component);
        }

        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);
    }

}
