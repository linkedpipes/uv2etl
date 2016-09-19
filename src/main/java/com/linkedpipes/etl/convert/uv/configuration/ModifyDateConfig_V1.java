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
@XStreamAlias("cz.cuni.mff.xrg.uv.transformer.ModifyDateConfig_V1")
public class ModifyDateConfig_V1 implements Configuration {

    private String inputPredicate = "http://localhost/temp/ontology/date";

    private int modifyDay = 1;

    private String outputPredicate = "http://localhost/temp/ontology/date";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component, boolean asTemplate) {

        pipeline.renameInPort(component, "input", "InputRdf");
        pipeline.renameOutPort(component, "output", "OutputRdf");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-modifyDate/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final IRI config = vf.createIRI("http://localhost/resources/configuration/t-modifyDate");
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(config, RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-modifyDate#Configuration")));

        st.add(vf.createStatement(config,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-modifyDate#input"),
                vf.createLiteral(inputPredicate)));

        st.add(vf.createStatement(config,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-modifyDate#shiftBy"),
                vf.createLiteral(modifyDay)));

        st.add(vf.createStatement(config,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-modifyDate#output"),
                vf.createLiteral(outputPredicate)));

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(config,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlConstruct#inputControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlConstruct#shiftByControl"),
                    force));

            st.add(vf.createStatement(config,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-sparqlConstruct#outputControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
