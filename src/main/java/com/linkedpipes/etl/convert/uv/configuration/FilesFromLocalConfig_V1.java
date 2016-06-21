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
@XStreamAlias("cz.cuni.mff.xrg.uv.extractor.filesfromlocal.FilesFromLocalConfig_V1")
class FilesFromLocalConfig_V1 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(FilesFromLocalConfig_V1.class);

    String source = "/tmp/";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        if (pipeline.removeInConnections(component, "config")) {
            LOG.warn("{} : Runtime configuration ignored.", component);
        }

        pipeline.renameOutPort(component, "output", "FilesOutput");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/e-filesFromLocal");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-filesFromLocal"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-filesFromLocal#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/e-filesFromLocal"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/e-filesFromLocal#path"),
                vf.createLiteral(source)));

        component.setLpConfiguration(st);

    }

}
