package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.LinkedList;
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
@XStreamAlias("cz.cuni.mff.xrg.uv.transformer.xslt.XsltConfig_V2")
class XsltConfig_V2 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(XsltConfig_V2.class);

    public static class Parameter {

        private String key = null;

        private String value = null;

        public Parameter() {
        }
    }

    public static class FileInformations {

        private List<Parameter> parameters = new LinkedList<>();

        private String symbolicName;

        public FileInformations() {
        }

    }

    String xsltTemplate = "";

    String xsltTemplateName = "Empty";

    boolean failOnError = true;

    String outputFileExtension = "";

    List<FileInformations> filesParameters = new LinkedList<>();

    int numberOfExtraThreads = 0;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {
        pipeline.renameInPort(component, "filesInput", "FilesInput");
        pipeline.renameOutPort(component, "filesOutput", "FilesOutput");
        if (pipeline.removeInConnections(component, "Parameters")) {
            LOG.warn("Runtime parameters ignored.");
        }

        component.setTemplate("http://etl.linkedpipes.com/resources/components/t-xslt/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-xslt"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-xslt#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-xslt"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-xslt#template"),
                vf.createLiteral(xsltTemplate)));

        // xsltTemplateName is not used, we can safely ignore it.
        if (!failOnError) {
            LOG.warn("{} : Will fail on error.", component);
        }

        if (outputFileExtension.startsWith(".")) {
            outputFileExtension = outputFileExtension.substring(1);
        }

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/t-xslt"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-xslt#extension"),
                vf.createLiteral(outputFileExtension)));

        if (!filesParameters.isEmpty()) {
            LOG.warn("{} : Parameters ignored.", component);
        }

        if (numberOfExtraThreads != 0) {
            LOG.info("{} : Single thread is used.", component);
        }

        component.setLpConfiguration(st);
    }

}
