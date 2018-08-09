package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.openrdf.model.IRI;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XStreamAlias("cz.cuni.mff.xrg.uv.transformer.check.rdfnotemtpy.RdfNotEmptyConfig_V1")
class RdfNotEmptyConfig_V1 implements Configuration {

    @XStreamAlias("eu.unifiedviews.dpu.DPUContext")
    public interface DPUContext {

        enum MessageType {
            DEBUG,
            INFO,
            WARNING,
            ERROR
        }

    }

    private static final Logger LOG
            = LoggerFactory.getLogger(RdfNotEmptyConfig_V1.class);

    public static final String AUTO_MESSAGE = "Check failed: Input dataUnit is empty!";

    private DPUContext.MessageType messageType = DPUContext.MessageType.ERROR;

    private String message = null;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component,
            boolean asTemplate) {
        if (messageType != null && messageType != DPUContext.MessageType.ERROR) {
            LOG.warn("{} : Will fail if there are no RDF data.", component);
        }
        if (message != null) {
            LOG.warn("{} : Custom message ignored.", component);
        }

        final Collection<LpPipeline.DataConnection> before
                = pipeline.getDataBefore(component, "rdf");
        final Collection<LpPipeline.DataConnection> after
                = pipeline.getDataAfter(component, "rdf");

        pipeline.renameInPort(component, "rdf", "InputRdf");

        // We need to add run after to the following component.
        for (LpPipeline.Connection connection : after) {
            pipeline.addRunAfter(connection.getSource(),
                    connection.getTarget());
        }

        // Remap input connections.
        for (LpPipeline.DataConnection out : after) {
            for (LpPipeline.DataConnection in : before) {
                pipeline.addDataConnection(in.getSource(), in.sourceBinding,
                        out.getTarget(), out.targetBinding);
            }
            pipeline.removeConnection(out);
        }

        component.setTemplate("http://etl.linkedpipes.com/resources/components/q-sparqlAsk/0.0.0");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/q-sparqlAsk"),
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/q-sparqlAsk#Configuration")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/q-sparqlAsk"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/q-sparqlAsk#query"),
                vf.createLiteral("ASK { ?s ?p ?o }")));

        st.add(vf.createStatement(
                vf.createIRI("http://localhost/resources/configuration/q-sparqlAsk"),
                vf.createIRI("http://plugins.linkedpipes.com/ontology/q-sparqlAsk#failOnTrue"),
                vf.createLiteral(false)));

        if (asTemplate) {
            final IRI force = vf.createIRI(
                    "http://plugins.linkedpipes.com/resource/configuration/Force");

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/q-sparqlAsk"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/q-sparqlAsk#queryControl"),
                    force));

            st.add(vf.createStatement(
                    vf.createIRI("http://localhost/resources/configuration/q-sparqlAsk"),
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/q-sparqlAsk#failOnTrueControl"),
                    force));
        }

        component.setLpConfiguration(st);
    }

}
