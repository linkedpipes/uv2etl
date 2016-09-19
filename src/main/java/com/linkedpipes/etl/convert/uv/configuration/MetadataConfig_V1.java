package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.TransformationReport;
import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Date;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.metadata.MetadataConfig_V1")
class MetadataConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(MetadataConfig_V1.class);

    private String comsodeDatasetId = "";

    private String outputGraphName = "http://localhost/metadata";

    private String datasetURI = "http://linked.opendata.cz/resource/dataset/";

    private String distroURI = "http://linked.opendata.cz/resource/dataset//distribution";

    /**
     * Language used for {@link title_cs}, {@link desc_cs}
     */
    private String language_cs = "cs";

    private String title_cs = "Název datasetu";

    private String title_en = "Dataset title";

    private String desc_cs = "Popis datasetu";

    private String desc_en = "Dataset description";

    private String mime = "application/zip";

    private LinkedList<String> authors = new LinkedList<>();

    private LinkedList<String> possibleAuthors = new LinkedList<>();

    private LinkedList<String> publishers = new LinkedList<>();

    private LinkedList<String> possiblePublishers = new LinkedList<>();

    private LinkedList<String> licenses = new LinkedList<>();

    private LinkedList<String> possibleLicenses = new LinkedList<>();

    private LinkedList<String> sources = new LinkedList<>();

    private LinkedList<String> possibleSources = new LinkedList<>();

    private LinkedList<String> exampleResources = new LinkedList<>();

    private LinkedList<String> possibleExampleResources = new LinkedList<>();

    private LinkedList<String> languages = new LinkedList<>();

    private LinkedList<String> possibleLanguages = new LinkedList<>();

    private LinkedList<String> keywords = new LinkedList<>();

    private LinkedList<String> possibleKeywords = new LinkedList<>();

    private LinkedList<String> themes = new LinkedList<>();

    private LinkedList<String> possibleThemes = new LinkedList<>();

    private String contactPoint = "http://opendata.cz/contacts";

    private String sparqlEndpoint = "http://linked.opendata.cz/sparql";

    private String dataDump = "http://linked.opendata.cz/dump/";

    private String periodicity = "http://purl.org/linked-data/sdmx/2009/code#freq-M";

    private boolean useNow = true;

    private boolean isQb = false;

    private Date modified = new Date();

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        // @DataUnit.AsInput(name = "data", optional = true)
        // @DataUnit.AsOutput(name = "metadata")

        LOG.error("{} : This component is not supported and so was removed.",
                component);
        TransformationReport.getInstance().unsupportedComponents(component,
                this);
        pipeline.removeComponent(component);
    }

}
