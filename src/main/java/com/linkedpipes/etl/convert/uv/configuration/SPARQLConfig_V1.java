package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.List;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.sparql.SPARQLConfig_V1")
class SPARQLConfig_V1 implements Configuration {

    @XStreamAlias("eu.unifiedviews.plugins.transformer.sparql.SPARQLQueryPair")
    public class SPARQLQueryPair {

        private String SPARQLQuery;

        private boolean isConstructType;

        public SPARQLQueryPair() {
        }

        public SPARQLQueryPair(String SPARQLQuery, boolean isConstructType) {
            this.SPARQLQuery = SPARQLQuery;
            this.isConstructType = isConstructType;
        }
    }

    private List<SPARQLQueryPair> queryPairs;

    private String SPARQL_Update_Query;

    boolean isConstructType;

    private String outputGraphSymbolicName = "T-SPARQL/output" + String.valueOf(new java.util.Random().nextInt(100));

    private boolean rewriteConstructToInsert = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        if (queryPairs.size() != 1) {
            throw new RuntimeException("Invalid number of queries.");
        }

        // Converts to:
        //  SparqlUpdateConfig_V1
        //  SparqlConstructConfig_V1
        final String query = queryPairs.get(0).SPARQLQuery;
        final boolean isConstruct = queryPairs.get(0).isConstructType;

        if (isConstruct) {
            SparqlConstructConfig_V1 config = new SparqlConstructConfig_V1();
            config.perGraph = false;
            config.query = query;
            config.update(pipeline, component);
        } else {
            SparqlUpdateConfig_V1 config = new SparqlUpdateConfig_V1();
            config.perGraph = false;
            config.query = query;
            config.update(pipeline, component);
        }

    }

}
