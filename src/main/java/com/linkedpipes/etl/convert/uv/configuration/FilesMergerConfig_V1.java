package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.TransformationReport;
import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.filesmerger.FilesMergerConfig_V1")
class FilesMergerConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(FilesMergerConfig_V1.class);

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        // @DataUnit.AsInput(name = "filesInput")
        // @DataUnit.AsOutput(name = "filesOutput")

        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);
    }

}
