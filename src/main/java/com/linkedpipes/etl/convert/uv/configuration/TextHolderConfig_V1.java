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

@XStreamAlias("cz.cuni.mff.xrg.uv.extractor.textholder.TextHolderConfig_V1")
class TextHolderConfig_V1 implements Configuration {

    String fileName = "triples.ttlA";

    String text = "<http://localhost/1> <<http://localhost/value> \"some text\"";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameOutPort(component, "file", "FilesOutput");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/e-textHolder/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-textHolder"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-textHolder#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-textHolder"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-textHolder#fileName"),
                vf.createLiteral(fileName)));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-textHolder"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-textHolder#content"),
                vf.createLiteral(text)));

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/e-textHolder"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-textHolder#fileNameControl"),
                    force));

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/e-textHolder"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/e-textHolder#contentControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
