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
@XStreamAlias("eu.unifiedviews.plugins.loader.filestoparliament.FilesToParliamentConfig_V1")
class FilesToParliamentConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(FilesToParliamentConfig_V1.class);

    private String endpointURL = "http://localhost:8080/parliament/";

    private String rdfFileFormat = "Auto";

    private String targetGraphName = "";

    private boolean clearDestinationGraph = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        // @DataUnit.AsInput(name = "input")
        // public FilesDataUnit filesInput;

        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);
    }

}
