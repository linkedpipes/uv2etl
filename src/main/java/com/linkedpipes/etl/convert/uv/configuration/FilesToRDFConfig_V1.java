package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.List;
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
@XStreamAlias("eu.unifiedviews.plugins.transformer.filestordft.FilesToRDFConfig_V1")
class FilesToRDFConfig_V1 implements Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(FilesToRDFConfig_V1.class);

    public static final String STOP_EXTRACTION_ERROR_HANDLING = "STOP_EXTRACTION";

    public static final String USE_INPUT_SYMBOLIC_NAME = "USE_INPUT_GRAPH_NAME";

    public static final String SET_RDF_TYPE = "AUTO";

    public static final String USE_FIXED_SYMBOLIC_NAME = "USE_SINGLE_SYMBOLIC_NAME";

    int commitSize = 100000;

    /**
     * Used to determine action if an exception is thrown during loading
     * of a single file.
     */
    String fatalErrorHandling = STOP_EXTRACTION_ERROR_HANDLING;

    /**
     * Policy for output graph naming.
     */
    String outputNaming = USE_INPUT_SYMBOLIC_NAME;

    /**
     * Manually added type of RDF graph
     */
    String outputType = SET_RDF_TYPE;

    /**
     * If outputNaming == USE_FIXED_SYMBOLIC_NAME then this value specify
     * name of output graph.
     */
    String outputSymbolicName = null;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        pipeline.renameInPort(component, "filesInput", "InputFiles");
        pipeline.renameOutPort(component, "rdfOutput", "OutputRdf");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-filesToRdf");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-filesToRdf"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-filesToRdf#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-filesToRdf"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-filesToRdf#commitSize"),
                vf.createLiteral(commitSize)));

        if (!STOP_EXTRACTION_ERROR_HANDLING.equals(fatalErrorHandling)) {
            // We stop and fail on every error !
            LOG.warn("{} : Will fail on the first error.", component);
        }

        if (!USE_FIXED_SYMBOLIC_NAME.equals(outputNaming)) {
            // Many to many.
            LOG.warn("{} : Multiple outputs graphs will be merged.",
                    component);
        }

        if (outputType == null || SET_RDF_TYPE.equals(outputType)) {
            // Leave empty, that cause autodetection.
        } else {
            // Stored as mime-type.
            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/t-filesToRdf"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-filesToRdf#mimeType"),
                    vf.createLiteral(outputType)));

        }

        if (outputSymbolicName != null) {
            LOG.warn("{} : 'outputSymbolicName' property ignored.", component);
        }

        // We do not use outputSymbolicName, as we never put data
        // into the one named graph. We need to insert the graph
        // merger after this component.
        component.setLpConfiguration(st);

        // Insert t-graphMerger and reconnect edges.
        final LpPipeline.Component merger = new LpPipeline.Component(
                "rdf-graph-merger",
                "[I]",
                component.getX(),
                component.getY() + 40);

        merger.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-graphMerger");

        pipeline.insertComponent(merger, 0, 60);
        pipeline.reconnectOutput(component, "OutputRdf", merger, "OutputRdf");
        pipeline.addDataConnection(component, "OutputRdf", merger, "InputRdf");
    }

}
