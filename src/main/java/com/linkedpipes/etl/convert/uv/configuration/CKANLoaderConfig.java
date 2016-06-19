package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.opendata.unifiedviews.dpus.ckan.CKANLoaderConfig")
class CKANLoaderConfig implements Configuration {

    private String apiUri = "http://ckan.opendata.cz/api/rest/dataset";

    private String apiKey = "";

    private boolean loadToCKAN = true;

    private String datasetID = "";

    private String filename = "ckan-api.json";

    private String orgID = "d2664e4e-25ba-4dcc-a842-dcc5f2d2f326";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        final CKANLoaderConfig_V3 config = new CKANLoaderConfig_V3();

        config.apiKey = apiKey;
        config.apiUri = apiUri.replace("/rest/dataset", "/3/action");
        config.datasetID = datasetID;
        config.filename = filename;
        config.loadToCKAN = loadToCKAN;
        config.orgID = orgID;

        config.update(pipeline, component);
    }

}
