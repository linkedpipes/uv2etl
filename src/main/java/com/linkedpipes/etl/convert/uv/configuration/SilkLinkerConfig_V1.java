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
@XStreamAlias("eu.unifiedviews.plugins.extractor.silklinker.SilkLinkerConfig_V1")
class SilkLinkerConfig_V1 implements Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(SilkLinkerConfig_V1.class);

    private String confFile = "";

    private String minConfirmedLinks = "0.9";

    private String minLinksToBeVerified = "0.0";

    private String confFileLabel = "";

    private String silkLibraryLocation = "";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsOutput(name = "links_confirmed")
        // @DataUnit.AsOutput(name = "links_to_be_verified", optional = true)

        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);
    }

}
