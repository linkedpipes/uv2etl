package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.openrdf.model.IRI;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.RDF;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.opendata.unifiedviews.dpus.distributionMetadata.DistributionMetadataConfig_V1")
class cz_DistributionMetadataConfig_V1 implements Configuration {

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

        pipeline.renameInPort(component, "datasetMetadata", "DatasetMetadata");
        pipeline.renameOutPort(component, "metadata", "Metadata");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/e-distributionMetadata");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI config = vf.createIRI("http://linkedpipes.com/resources/e-distributionMetadata");
        final String prefix = "http://plugins.linkedpipes.com/ontology/e-distributionMetadata#";
        st.add(vf.createStatement(config,
                RDF.TYPE,
                vf.createIRI(prefix + "Configuration")));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "datasetURI"),
                vf.createLiteral(datasetURI)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "distributionURI"),
                vf.createLiteral(distributionURI)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "useDatasetURIfromInput"),
                vf.createLiteral(useDatasetURIfromInput)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "language_orig"),
                vf.createLiteral(language_orig)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "title_orig"),
                vf.createLiteral(title_orig)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "title_en"),
                vf.createLiteral(title_en)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "desc_orig"),
                vf.createLiteral(desc_orig)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "desc_en"),
                vf.createLiteral(desc_en)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "license"),
                vf.createLiteral(license)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "sparqlEndpointUrl"),
                vf.createLiteral(sparqlEndpointUrl)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "mediaType"),
                vf.createLiteral(mediaType)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "downloadURL"),
                vf.createLiteral(downloadURL)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "accessURL"),
                vf.createLiteral(accessURL)));

        for (String item : exampleResources) {
            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "exampleResources"),
                    vf.createLiteral(item)));
        }

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "useNow"),
                vf.createLiteral(useNow)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "modified"),
                vf.createLiteral(modified)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "issued"),
                vf.createLiteral(issued)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "titleFromDataset"),
                vf.createLiteral(titleFromDataset)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "generateDistroURIFromDataset"),
                vf.createLiteral(generateDistroURIFromDataset)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "originalLanguageFromDataset"),
                vf.createLiteral(originalLanguageFromDataset)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "issuedFromDataset"),
                vf.createLiteral(issuedFromDataset)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "descriptionFromDataset"),
                vf.createLiteral(descriptionFromDataset)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "licenseFromDataset"),
                vf.createLiteral(licenseFromDataset)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "schemaFromDataset"),
                vf.createLiteral(schemaFromDataset)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "useTemporal"),
                vf.createLiteral(useTemporal)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "useNowTemporalEnd"),
                vf.createLiteral(useNowTemporalEnd)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "temporalFromDataset"),
                vf.createLiteral(temporalFromDataset)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "temporalEnd"),
                vf.createLiteral(temporalEnd)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "temporalStart"),
                vf.createLiteral(temporalStart)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "schema"),
                vf.createLiteral(schema)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "schemaType"),
                vf.createLiteral(schemaType)));

        component.setLpConfiguration(st);

    }

}
