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
@XStreamAlias("eu.unifiedviews.cssz.Xls2csvConfig_V1")
class Xls2csvConfig_V1 implements Configuration {

    private String template_prefix = "SABLONA_";

    private String prefix = "\\\\%\\\\%";

    private String suffix = "\\\\%\\\\%";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameInPort(component, "input", "Xls");
        pipeline.renameInPort(component, "inputTemplates", "Templates");
        pipeline.renameOutPort(component, "output", "Output");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-templatedXlsToCsv/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI configuration = vf.createIRI("http://localhost/resources/configuration/t-templatedXlsToCsv");

        st.add(vf.createStatement(configuration, RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-templatedXlsToCsv#Configuration")));

        st.add(vf.createStatement(configuration,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-templatedXlsToCsv#prefix"),
                vf.createLiteral(template_prefix)));

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(configuration,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-templatedXlsToCsv#prefixControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
