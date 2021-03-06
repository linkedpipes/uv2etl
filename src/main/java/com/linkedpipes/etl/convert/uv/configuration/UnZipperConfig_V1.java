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

@XStreamAlias("eu.unifiedviews.plugins.transformer.unzipper.UnZipperConfig_V1")
class UnZipperConfig_V1 implements Configuration {

    boolean notPrefixed = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {

        pipeline.renameInPort(component, "input", "FilesInput");
        pipeline.renameOutPort(component, "output", "FilesOutput");

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-unpackZip/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-unpackZip"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-unpackZip#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-unpackZip"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-unpackZip#usePrefix"),
                vf.createLiteral(!notPrefixed)));

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/t-unpackZip"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-unpackZip#usePrefixControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
