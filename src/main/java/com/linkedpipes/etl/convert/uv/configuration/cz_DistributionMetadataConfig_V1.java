package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.opendata.unifiedviews.dpus.distributionMetadata.DistributionMetadataConfig_V1")
class cz_DistributionMetadataConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(cz_DistributionMetadataConfig_V1.class);

    private String datasetURI = "";

    private String distributionURI = "";

    private boolean useDatasetURIfromInput = true;

    private String language_orig = "";

    private String title_orig = "";

    private String title_en = "";

    private String desc_orig = "";

    private String desc_en = "";

    private String license = "";

    private String sparqlEndpointUrl = "";

    private String mediaType = "";

    private String downloadURL = "";

    private String accessURL = "";

    private Collection<String> exampleResources = new LinkedList<>();

    private boolean useNow = true;

    private Date modified = new Date();

    private Date issued = new Date();

    private boolean titleFromDataset = true;

    private boolean generateDistroURIFromDataset = true;

    private boolean originalLanguageFromDataset = true;

    private boolean issuedFromDataset = true;

    private boolean descriptionFromDataset = true;

    private boolean licenseFromDataset = true;

    private boolean schemaFromDataset = true;

    private boolean useTemporal = true;

    private boolean useNowTemporalEnd = false;

    private boolean temporalFromDataset = true;

    private Date temporalEnd = new Date();

    private Date temporalStart = new Date();

    private String schema = "";

    private String schemaType = "";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsInput(name = "datasetMetadata", optional = true)
        // @DataUnit.AsOutput(name = "metadata")

        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);
    }

}
