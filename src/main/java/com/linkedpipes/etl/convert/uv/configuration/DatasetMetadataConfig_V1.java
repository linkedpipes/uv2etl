package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.opendata.unifiedviews.dpus.datasetMetadata.DatasetMetadataConfig_V1")
class DatasetMetadataConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(DatasetMetadataConfig_V1.class);

    String datasetURI = "";

    String distributionURI = "";

    boolean useDatasetURIfromInput = true;

    String language_orig = "";

    String title_cs = "";

    String title_en = "";

    String desc_cs = "";

    String desc_en = "";

    Collection<String> authors = new LinkedList<>();

    String publisherURI = "http://opendata.cz";

    String publisherName = "Opendata.cz";

    String license = "";

    Collection<String> sources = new LinkedList<>();

    Collection<String> languages = new LinkedList<>();

    Collection<String> keywords_orig = new LinkedList<>();

    Collection<String> keywords_en = new LinkedList<>();

    Collection<String> themes = new LinkedList<>();

    String contactPoint = "";

    String contactPointName = "";

    String periodicity = "";

    boolean useNow = true;

    boolean useNowTemporalEnd = false;

    boolean useTemporal = true;

    Date modified = new Date();

    Date issued = new Date();

    String identifier = "";

    String landingPage = "";

    Date temporalEnd = new Date();

    Date temporalStart = new Date();

    String spatial = "";

    String schema = "";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameOutPort(component, "metadata", "Metadata");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/e-datasetMetadata/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final Resource configuration
                = vf.createIRI("http://localhost/resources/configuration/e-datasetMetadata");

        st.add(vf.createStatement(configuration,
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#Configuration")));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#datasetURI"),
                vf.createLiteral(datasetURI)));

        if (distributionURI != null) {
            LOG.debug("{} : 'distributionURI' ignored.", component);
        }

        if (useDatasetURIfromInput) {
            LOG.info("{} : 'useDatasetURIfromInput' ignored.", component);
        }

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#language_orig"),
                vf.createLiteral(language_orig)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#title_cs"),
                vf.createLiteral(title_cs)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#title_en"),
                vf.createLiteral(title_en)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#desc_cs"),
                vf.createLiteral(desc_cs)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#desc_en"),
                vf.createLiteral(desc_en)));

        for (String item : authors) {
            st.add(vf.createStatement(configuration,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#authors"),
                    vf.createLiteral(item)));
        }

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#publisherURI"),
                vf.createLiteral(publisherURI)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#publisherName"),
                vf.createLiteral(publisherName)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#license"),
                vf.createLiteral(license)));

        for (String item : sources) {
            st.add(vf.createStatement(configuration,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#sources"),
                    vf.createLiteral(item)));
        }

        for (String item : languages) {
            st.add(vf.createStatement(configuration,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#languages"),
                    vf.createLiteral(item)));
        }

        for (String item : keywords_orig) {
            st.add(vf.createStatement(configuration,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#keywords_orig"),
                    vf.createLiteral(item)));
        }

        for (String item : keywords_en) {
            st.add(vf.createStatement(configuration,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#keywords_en"),
                    vf.createLiteral(item)));
        }

        for (String item : themes) {
            st.add(vf.createStatement(configuration,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#themes"),
                    vf.createLiteral(item)));
        }

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#contactPoint"),
                vf.createLiteral(contactPoint)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#contactPointName"),
                vf.createLiteral(contactPointName)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#periodicity"),
                vf.createLiteral(periodicity)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#useNow"),
                vf.createLiteral(useNow)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#useNowTemporalEnd"),
                vf.createLiteral(useNowTemporalEnd)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#useTemporal"),
                vf.createLiteral(useTemporal)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#modified"),
                vf.createLiteral(modified)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#issued"),
                vf.createLiteral(issued)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#identifier"),
                vf.createLiteral(identifier)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#landingPage"),
                vf.createLiteral(landingPage)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#temporalEnd"),
                vf.createLiteral(temporalEnd)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#temporalStart"),
                vf.createLiteral(temporalStart)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#spatial"),
                vf.createLiteral(spatial)));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-datasetMetadata#schema"),
                vf.createLiteral(schema)));

        component.setLpConfiguration(st);

    }

}
