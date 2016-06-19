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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.filesrenamer.RenamerConfig_V1")
class RenamerConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(RenamerConfig_V1.class);

    private String pattern = "xml";

    private String replaceText = "ttl";

    /**
     * If true the replace
     */
    private boolean useAdvanceReplace = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        if (useAdvanceReplace) {
            LOG.error("{} : 'useAdvanceReplace' option is not supported. "
                    + "Component will be removed.", component);
            pipeline.removeComponent(component);
        }

        pipeline.renameInPort(component, "input", "InputFiles");
        pipeline.renameOutPort(component, "output", "OutputFiles");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-filesRenamer");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI config = vf.createIRI("http://linkedpipes.com/resources/t-filesRenamer");
        final String prefix = "http://plugins.linkedpipes.com/ontology/t-filesRenamer#";

        st.add(vf.createStatement(config,
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-filesFilter#Configuration")));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "pattern"),
                vf.createLiteral(pattern)));

        st.add(vf.createStatement(config,
                vf.createIRI(prefix + "replaceWith"),
                vf.createLiteral(replaceText)));

        LOG.info("Please check configuration of this component.");

        component.setLpConfiguration(st);

    }

}
