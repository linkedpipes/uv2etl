package com.linkedpipes.etl.convert.uv.pipeline;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.HashSet;
import java.util.Set;

/**
 * UvPipeline model for loading from UV.
 */
@XStreamAlias("cz.cuni.mff.xrg.odcs.commons.app.pipeline.Pipeline")
public class UvPipeline {

    @XStreamAlias("cz.cuni.mff.xrg.odcs.commons.app.dpu.DPUTemplateRecord")
    public static class Template {

        private String description;

        private String name;

        private String jarName;

        private String rawConf;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public String getJarName() {
            return jarName;
        }

    }

    @XStreamAlias("cz.cuni.mff.xrg.odcs.commons.app.dpu.DPUInstanceRecord")
    public static class Instance {

        private String description;

        private String name;

        private Template template;

        private boolean useTemplateConfig;

        private String rawConf;

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public String getUsedConfig() {
            if (useTemplateConfig) {
                return template.rawConf;
            } else {
                return rawConf;
            }
        }

        public boolean isUseTemplateConfig() {
            return useTemplateConfig;
        }

        public Template getTemplate() {
            return template;
        }

    }

    @XStreamAlias("cz.cuni.mff.xrg.odcs.commons.app.pipeline.graph.Position")
    public static class Position {

        private int x;

        private int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }

    @XStreamAlias("cz.cuni.mff.xrg.odcs.commons.app.pipeline.graph.Edge")
    public static class Edge {

        private String script;

        private Node from;

        private Node to;

        public String getScript() {
            return script;
        }

        public Node getFrom() {
            return from;
        }

        public Node getTo() {
            return to;
        }

    }

    @XStreamAlias("cz.cuni.mff.xrg.odcs.commons.app.pipeline.graph.Node")
    public static class Node {

        private Instance dpuInstance;

        private Position position;

        public Instance getDpuInstance() {
            return dpuInstance;
        }

        public Position getPosition() {
            return position;
        }
    }

    @XStreamAlias("com.linkedpipes.elt.convert.uv.pipeline.Pipeline$Graph")
    public static class Graph {

        private Set<Node> nodes = new HashSet<>();

        private Set<Edge> edges = new HashSet<>();

        public Set<Node> getNodes() {
            return nodes;
        }

        public Set<Edge> getEdges() {
            return edges;
        }

    }

    private String name;

    private Graph graph;

    public String getName() {
        return name;
    }

    public Graph getGraph() {
        return graph;
    }

}
