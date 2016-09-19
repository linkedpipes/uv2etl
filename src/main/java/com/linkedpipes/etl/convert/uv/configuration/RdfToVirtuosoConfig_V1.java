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
@XStreamAlias("eu.unifiedviews.plugins.loader.rdftovirtuoso.RdfToVirtuosoConfig_V1")
class RdfToVirtuosoConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(RdfToVirtuosoConfig_V1.class);

    private String virtuosoUrl = "jdbc:virtuoso://localhost:1111/charset=UTF-8/";

    private String username = "dba";

    private String password = "dba";

    private boolean clearDestinationGraph = false;

    private String targetGraphName = "";

    private int threadCount = 0;

    private boolean skipOnError = false;

    private int commitSize = 100000;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        // @DataUnit.AsInput(name = "rdfInput")
        // @DataUnit.AsOutput(name = "rdfOutput", optional = true)

        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);
    }

}
