package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.TransformationReport;
import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
class UnknownConfiguration implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(UnknownConfiguration.class);

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {
        LOG.error("{} : Unknown unsupported component.", component);
        TransformationReport.getInstance().unknownComponents(component);
        pipeline.removeComponent(component);
    }

}
