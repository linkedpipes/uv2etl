package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.Arrays;
import java.util.List;
import org.openrdf.rio.RDFFormat;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("eu.unifiedviews.plugins.transformer.rdftofiles.RdfToFilesConfig_V1")
class RdfToFilesConfig_V1 implements Configuration {

    @XStreamAlias("eu.unifiedviews.plugins.transformer.rdftofiles.RdfToFilesConfig_V1$GraphToFileInfo")
    public class GraphToFileInfo {

        private String inSymbolicName;

        private String outFileName;

        public GraphToFileInfo() {
        }

        public GraphToFileInfo(String inSymbolicName, String outFileName,
                String outputGraphName) {
            this.inSymbolicName = outFileName;
            this.outFileName = outFileName;
        }
    }

    private String rdfFileFormat = RDFFormat.TURTLE.getName();

    private boolean genGraphFile = true;

    private boolean mergeGraphs = true;

    private String outGraphName = "";

    private List<GraphToFileInfo> graphToFileInfo
            = Arrays.asList(new GraphToFileInfo("", "data", ""));

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        final RdfToFilesConfig_V2 config = new RdfToFilesConfig_V2();

        config.genGraphFile = this.genGraphFile;
        config.outFileName = this.graphToFileInfo.get(0).outFileName;
        config.outGraphName = this.outGraphName;
        config.rdfFileFormat = this.rdfFileFormat;

        config.update(pipeline, component);
    }

}
