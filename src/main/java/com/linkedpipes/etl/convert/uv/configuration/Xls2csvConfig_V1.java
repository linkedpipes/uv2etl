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
@XStreamAlias("eu.unifiedviews.cssz.Xls2csvConfig_V1")
public class Xls2csvConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(Xls2csvConfig_V1.class);

    private String template_prefix = "SABLONA_";

    private String prefix = "\\\\%\\\\%";

    private String suffix = "\\\\%\\\\%";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "input")
        // @DataUnit.AsInput(name = "inputTemplates")
        // @DataUnit.AsOutput(name = "output")
        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);

    }

}
