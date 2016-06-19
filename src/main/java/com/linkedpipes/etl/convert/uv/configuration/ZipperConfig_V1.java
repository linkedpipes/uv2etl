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
@XStreamAlias("eu.unifiedviews.plugins.transformer.zipper.ZipperConfig_V1")
class ZipperConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(ZipperConfig_V1.class);

    private String zipFile = "data.zip";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "input")
        // @DataUnit.AsOutput(name = "output")

        // t-packZip
        // fileName

        // @Component.InputPort(id = "FilesInput")
        // @Component.OutputPort(id = "FilesOutput")

        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);
    }

}
