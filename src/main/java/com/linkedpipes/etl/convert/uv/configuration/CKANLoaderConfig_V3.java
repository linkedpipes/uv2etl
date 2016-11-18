package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
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
@XStreamAlias("cz.opendata.unifiedviews.dpus.ckan.CKANLoaderConfig_V3")
class CKANLoaderConfig_V3 implements Configuration {

    String apiUri = "http://ckan.opendata.cz/api/3/action";

    String apiKey = "";

    boolean loadToCKAN = true;

    String datasetID = "";

    String filename = "ckan-api.json";

    String orgID = "d2664e4e-25ba-4dcc-a842-dcc5f2d2f326";

    String loadLanguage = "cs";

    boolean generateVirtuosoTurtleExampleResource = true;

    boolean generateExampleResource = true;

    boolean overwrite = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameInPort(component, "metadata", "Metadata");
        pipeline.renameOutPort(component, "JSON", "OutputFiles");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/l-dcatApToCkan/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI config = vf.createIRI("http://linkedpipes.com/resources/l-dcatApToCkan");
        final String prefix = "http://plugins.linkedpipes.com/ontology/l-dcatApToCkan#";
        st.add(vf.createStatement(config,
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/l-dcatApToCkan")));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "apiUrl"),
                vf.createLiteral(apiUri)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "apiKey"),
                vf.createLiteral(apiKey)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "loadToCkan"),
                vf.createLiteral(loadToCKAN)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "datasetID"),
                vf.createLiteral(datasetID)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "filename"),
                vf.createLiteral(filename)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "organizationId"),
                vf.createLiteral(orgID)));

        if (loadLanguage != null) {
            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "loadLanguage"),
                    vf.createLiteral(loadLanguage)));
        }

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "generateVirtuosoExample"),
                vf.createLiteral(generateVirtuosoTurtleExampleResource)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "generateExample"),
                vf.createLiteral(generateExampleResource)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "overwrite"),
                vf.createLiteral(overwrite)));

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "apiUrlControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "apiKeyControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "loadToCkanControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "datasetIDControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "filenameControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "organizationIdControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "loadLanguageControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "generateVirtuosoExampleControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "generateExampleControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI(prefix + "overwriteControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
