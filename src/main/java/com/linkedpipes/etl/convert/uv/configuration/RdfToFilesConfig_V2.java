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
import org.openrdf.rio.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.rdftofiles.RdfToFilesConfig_V2")
class RdfToFilesConfig_V2 implements Configuration {

    private static final Logger LOG
            = LoggerFactory.getLogger(RdfToFilesConfig_V2.class);

    String outFileName = "output";

    String rdfFileFormat = RDFFormat.TURTLE.getName();

    boolean genGraphFile = true;

    String outGraphName = "http://localhost/resource/output";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        if (pipeline.removeInConnections(component, "config")) {
            LOG.warn("{} : Runtime configuration ignored.", component);
        }

        pipeline.renameInPort(component, "input", "InputRdf");
        pipeline.renameOutPort(component, "output", "OutputFile");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-rdfToFile");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        final IRI resource = vf.createIRI("http://localhost/resources/configuration/t-rdfToFile");

        st.add(vf.createStatement(
                resource,
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-rdfToFile#Configuration")));

        RDFFormat format;
        if (rdfFileFormat == null) {
            format = null;
        } else {
            switch (rdfFileFormat) {
                case "RDF/XML":
                    format = RDFFormat.RDFXML;
                    break;
                case "N-Triples":
                    format = RDFFormat.NTRIPLES;
                    break;
                case "Turtle":
                    format = RDFFormat.TURTLE;
                    break;
                case "N3":
                    format = RDFFormat.N3;
                    break;
                case "TriX":
                    format = RDFFormat.TRIX;
                    break;
                case "TriG":
                    format = RDFFormat.TRIG;
                    break;
                case "BinaryRDF":
                    format = RDFFormat.BINARY;
                    break;
                case "N-Quads":
                    format = RDFFormat.NQUADS;
                    break;
                case "JSON-LD":
                    format = RDFFormat.JSONLD;
                    break;
                case "RDF/JSON":
                    format = RDFFormat.RDFJSON;
                    break;
                case "RDFa":
                    format = RDFFormat.RDFA;
                    break;
                default:
                    throw new RuntimeException("Unknown RDF type: "
                            + rdfFileFormat);
            }
        }

        // The 'genGraphFile' option is disabled for formats with contexts.
        if (genGraphFile) {
            if (format == null || !format.supportsContexts()) {
                LOG.warn("{} : Ignore generate graph file options.", component);
            }
        }

        if (format != null) {
            st.add(vf.createStatement(
                    resource,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-rdfToFile#fileType"),
                    vf.createLiteral(format.getDefaultMIMEType())));
            // Update file name as UV has widhout and LP with extension.
            outFileName += format.getDefaultFileExtension();
        }

        st.add(vf.createStatement(
                resource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-rdfToFile#fileName"),
                vf.createLiteral(outFileName)));

        st.add(vf.createStatement(
                resource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-rdfToFile#graphUri"),
                vf.createLiteral(outGraphName)));

        component.setLpConfiguration(st);

    }

}
