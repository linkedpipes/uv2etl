package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.TransformationReport;
import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.extractor.distributionmetadata.DistributionMetadataConfig_V1")
class DistributionMetadataConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(DistributionMetadataConfig_V1.class);

    private String name;

    private String description;

    private String format;

    private String mimetype;

    private Date created;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        // @DataUnit.AsOutput(name = "distributionOutput")

        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);
    }

}