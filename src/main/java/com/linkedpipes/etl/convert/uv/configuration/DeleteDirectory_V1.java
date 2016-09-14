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
@XStreamAlias("cz.cuni.mff.xrg.uv.loader.deleteDirectory.DeleteDirectory_V1")
class DeleteDirectory_V1 implements Configuration {

    private String directory = "/tmp/PUT_DIRECTORY_PATH_HERE";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        component.setTemplate("http://etl.linkedpipes.com/resources/components/x-deleteDirectory");

        final String PREFIX = "http://plugins.linkedpipes.com/ontology/x-deleteDirectory#";
        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI resource = vf.createIRI("http://localhost/resources/configuration/x-deleteDirectory/0.0.0");

        st.add(vf.createStatement(resource, RDF.TYPE,
                vf.createIRI(PREFIX + "Configuration")));

        st.add(vf.createStatement(resource,
                vf.createIRI(PREFIX + "directory"),
                vf.createLiteral(directory)));

        component.setLpConfiguration(st);

    }

}
