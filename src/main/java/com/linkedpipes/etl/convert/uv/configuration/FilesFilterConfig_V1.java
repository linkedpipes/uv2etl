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
@XStreamAlias("eu.unifiedviews.plugins.transformer.filesfilter.FilesFilterConfig_V1")
class FilesFilterConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(FilesFilterConfig_V1.class);

    public static final String SYMBOLIC_NAME = "FIXED_SYMBOLIC_NAME";

    public static final String VIRTUAL_PATH = "VIRTUAL_PATH";

    private String predicate = SYMBOLIC_NAME;

    private String object = ".*";

    private boolean useRegExp = true;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        pipeline.renameInPort(component, "input", "InputFiles");
        pipeline.renameOutPort(component, "output", "OutputFiles");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-filesFilter");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-filesFilter"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-filesFilter#Configuration")));

        String pattern = object;
        if (!useRegExp) {
            pattern = ".*" + pattern + ".*";
        } else {
            // Update pattern.
            if (!pattern.startsWith("^")) {
                pattern = ".*" + pattern;
            }
            if (!pattern.endsWith("$")) {
                pattern += ".*";
            }
            LOG.info("{} : Check file name pattern: {} -> {}", component, object, pattern);
        }

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-filesFilter"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-filesFilter#fileNamePattern"),
                vf.createLiteral(pattern)));

        if (predicate.equals(SYMBOLIC_NAME)) {
            LOG.warn("{} : Symblic name no longer exists.", component);
        }

        LOG.warn("Please check configuration of this component.");

        component.setLpConfiguration(st);
    }

}
