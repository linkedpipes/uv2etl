package com.linkedpipes.etl.convert.uv.pipeline;

import com.linkedpipes.etl.convert.uv.configuration.Configuration;
import com.linkedpipes.etl.convert.uv.configuration.ConfigurationLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.openrdf.model.IRI;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.SKOS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represent LinkedPipes pipeline model.
 *
 * @author Petr Škoda
 */
public class LpPipeline {

    private static final float SCALE_X = 1.2f;

    private static final float SCALE_Y = 1.2f;

    public static class Component {

        private IRI iri;

        private final String label;

        private final String description;

        private String template;

        private int x;

        private int y;

        private List<Statement> lpConfiguration = Collections.EMPTY_LIST;

        private Configuration uvConfiguration;

        private boolean updated = false;

        public Component(String name, String description, int x, int y) {
            this.label = limitSize(name);
            this.description = limitSize(description);
            this.x = (int) (x * SCALE_X);
            this.y = (int) (y * SCALE_Y);
        }

        public String getLabel() {
            return label;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public List<Statement> getLpConfiguration() {
            return lpConfiguration;
        }

        public void setLpConfiguration(List<Statement> lpConfiguration) {
            this.lpConfiguration = lpConfiguration;
        }

        public Configuration getUvConfiguration() {
            return uvConfiguration;
        }

        public void setUvConfiguration(Configuration uvConfiguration) {
            this.uvConfiguration = uvConfiguration;
        }

        public boolean isUpdated() {
            return updated;
        }

        public void setUpdated(boolean updated) {
            this.updated = updated;
        }

        @Override
        public String toString() {
            return "[" + label + "]";
        }

        private static String limitSize(String string) {
            if (string.length() < 25) {
                return string;
            }
            final StringBuffer newString
                    = new StringBuffer(string.length() + string.length() / 25);
            // TODO Use full description here.
            newString.append(string.substring(0, 17)).append("...");
            //
            return newString.toString();
        }

    }

    public abstract static class Connection {

        private Component source;

        private Component target;

        public Connection(Component source, Component target) {
            this.source = source;
            this.target = target;
        }

        public void write(ValueFactory vf, IRI iri, IRI graph,
                List<Statement> collector) {
            collector.add(vf.createStatement(iri,
                    vf.createIRI("http://linkedpipes.com/ontology/sourceComponent"),
                    source.iri,
                    graph));
            collector.add(vf.createStatement(iri,
                    vf.createIRI("http://linkedpipes.com/ontology/targetComponent"),
                    target.iri,
                    graph));
        }

        public Component getSource() {
            return source;
        }

        public Component getTarget() {
            return target;
        }

    }

    public static class RunAfter extends Connection {

        public RunAfter(Component source, Component target) {
            super(source, target);
        }

        @Override
        public void write(ValueFactory vf, IRI iri, IRI graph,
                List<Statement> collector) {
            super.write(vf, iri, graph, collector);
            collector.add(vf.createStatement(iri, RDF.TYPE,
                    vf.createIRI("http://linkedpipes.com/ontology/RunAfter"),
                    graph));
        }

    }

    public static class DataConnection extends Connection {

        public String sourceBinding;

        public String targetBinding;

        public DataConnection(Component source, String sourceBinding,
                Component target, String targetBinding) {
            super(source, target);
            this.sourceBinding = sourceBinding;
            this.targetBinding = targetBinding;
        }

        @Override
        public void write(ValueFactory vf, IRI iri, IRI graph,
                List<Statement> collector) {
            super.write(vf, iri, graph, collector);
            collector.add(vf.createStatement(iri, RDF.TYPE,
                    vf.createIRI("http://linkedpipes.com/ontology/Connection"),
                    graph));
            collector.add(vf.createStatement(iri,
                    vf.createIRI("http://linkedpipes.com/ontology/sourceBinding"),
                    vf.createLiteral(sourceBinding),
                    graph));
            collector.add(vf.createStatement(iri,
                    vf.createIRI("http://linkedpipes.com/ontology/targetBinding"),
                    vf.createLiteral(targetBinding),
                    graph));
        }

    }

    private static final Logger LOG = LoggerFactory.getLogger(LpPipeline.class);

    private String label;

    private final List<Component> components = new LinkedList<>();

    private final List<Connection> connections = new LinkedList<>();

    private LpPipeline() {

    }

    /**
     * Add a component to given place, move all other components.
     *
     * @param component
     * @param x How much shift components.
     * @param y How much shift components.
     */
    public void insertComponent(Component component, int x, int y) {
        for (Component item : components) {
            if (item.x > component.x) {
                item.x += x;
            }
            if (item.y > component.y) {
                item.y += y;
            }
        }
        components.add(component);
    }

    /**
     * Remove component and all its connections.
     *
     * @param component
     */
    public void removeComponent(Component component) {
        components.remove(component);
        final Collection<Connection> toRemove = new LinkedList<>();
        for (Connection connection : connections) {
            if (connection.source == component
                    || connection.target == component) {
                toRemove.add(connection);
            }
        }
        connections.removeAll(toRemove);
    }

    /**
     *
     * @param component
     * @param binding
     * @return True if some edge was removed.
     */
    public boolean removeInConnections(Component component, String binding) {
        final Collection<Connection> toRemove = new LinkedList<>();
        for (Connection connection : connections) {
            if (connection instanceof DataConnection) {
                final DataConnection dataConnection
                        = (DataConnection) connection;
                if (dataConnection.getTarget() == component
                        && dataConnection.targetBinding.equals(binding)) {
                    toRemove.add(connection);
                }
            }
        }
        connections.removeAll(toRemove);
        return !toRemove.isEmpty();
    }

    /**
     *
     * @param component
     * @param binding
     * @return True if some edge was removed.
     */
    public boolean removeOutConnections(Component component, String binding) {
        final Collection<Connection> toRemove = new LinkedList<>();
        for (Connection connection : connections) {
            if (connection instanceof DataConnection) {
                final DataConnection dataConnection
                        = (DataConnection) connection;
                if (dataConnection.getSource() == component
                        && dataConnection.sourceBinding.equals(binding)) {
                    toRemove.add(connection);
                }
            }
        }
        connections.removeAll(toRemove);
        return !toRemove.isEmpty();
    }

    /**
     * Reconnect outputs of given component to outputs of the other component.
     *
     * @param oldComponent
     * @param oldBinding
     * @param newComponent
     * @param newBinding
     */
    public void reconnectOutput(Component oldComponent, String oldBinding,
            Component newComponent, String newBinding) {
        final Collection<Connection> toRemove = new LinkedList<>();
        // Scan for connections to replace.
        for (Connection connection : connections) {
            if (connection.source == oldComponent) {
                if (connection instanceof DataConnection) {
                    toRemove.add(connection);
                } else if (connection instanceof DataConnection) {
                    final DataConnection dataConnection
                            = (DataConnection) connection;
                    if (dataConnection.sourceBinding.equals(oldBinding)) {
                        toRemove.add(connection);
                    }
                }
            }
        }
        // Add new.
        for (Connection connection : toRemove) {
            if (connection instanceof RunAfter) {
                connections.add(new RunAfter(newComponent, connection.target));
            } else if (connection instanceof DataConnection) {
                final DataConnection dataConnection
                        = (DataConnection) connection;
                connections.add(new DataConnection(newComponent, newBinding,
                        connection.target, dataConnection.targetBinding));
            }
        }
        // Remove old.
        connections.removeAll(toRemove);
    }

    /**
     * Reconnect input of given component to outputs of the other component.
     *
     * @param oldComponent
     * @param oldBinding
     * @param newComponent
     * @param newBinding
     */
    public void reconnectInput(Component oldComponent, String oldBinding,
            Component newComponent, String newBinding) {
        final Collection<Connection> toRemove = new LinkedList<>();
        // Scan for connections to replace.
        for (Connection connection : connections) {
            if (connection.target == oldComponent) {
                if (connection instanceof DataConnection) {
                    toRemove.add(connection);
                } else if (connection instanceof DataConnection) {
                    final DataConnection dataConnection
                            = (DataConnection) connection;
                    if (dataConnection.targetBinding.equals(oldBinding)) {
                        toRemove.add(connection);
                    }
                }
            }
        }
        // Add new.
        for (Connection connection : toRemove) {
            if (connection instanceof RunAfter) {
                connections.add(new RunAfter(connection.source, newComponent));
            } else if (connection instanceof DataConnection) {
                final DataConnection dataConnection
                        = (DataConnection) connection;
                connections.add(new DataConnection(connection.source,
                        dataConnection.sourceBinding,
                        newComponent, newBinding));
            }
        }
        // Remove old.
        connections.removeAll(toRemove);
    }

    public void renameInPort(Component component, String oldName, String newName) {
        for (Connection connection : connections) {
            if (connection instanceof DataConnection) {
                final DataConnection dataConnection
                        = (DataConnection) connection;
                if (dataConnection.getTarget() == component
                        && dataConnection.targetBinding.equals(oldName)) {
                    dataConnection.targetBinding = newName;
                }
            }
        }
    }

    public void renameOutPort(Component component, String oldName, String newName) {
        for (Connection connection : connections) {
            if (connection instanceof DataConnection) {
                final DataConnection dataConnection
                        = (DataConnection) connection;
                if (dataConnection.getSource() == component
                        && dataConnection.sourceBinding.equals(oldName)) {
                    dataConnection.sourceBinding = newName;
                }
            }
        }
    }

    public Collection<DataConnection> getDataBefore(Component component, String binding) {
        final List<DataConnection> result = new LinkedList<>();
        for (Connection connection : connections) {
            if (connection.target == component) {
                if (connection instanceof DataConnection) {
                    final DataConnection dataConn = (DataConnection) connection;
                    if (dataConn.targetBinding.equals(binding)) {
                        result.add(dataConn);
                    }
                }
            }
        }
        return result;
    }

    public Collection<DataConnection> getDataAfter(Component component, String binding) {
        final List<DataConnection> result = new LinkedList<>();
        for (Connection connection : connections) {
            if (connection.source == component) {
                if (connection instanceof DataConnection) {
                    final DataConnection dataConn = (DataConnection) connection;
                    if (dataConn.sourceBinding.equals(binding)) {
                        result.add(dataConn);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Add a data connection.
     *
     * @param source
     * @param sourceBinding
     * @param target
     * @param targetBinding
     */
    public void addDataConnection(Component source, String sourceBinding,
            Component target, String targetBinding) {
        connections.add(new DataConnection(source, sourceBinding, target,
                targetBinding));
    }

    public void addRunAfter(Component source, Component target) {
        connections.add(new RunAfter(source, target));
    }

    /**
     * Remove connection.
     *
     * @param connection
     */
    public void removeConnection(Connection connection) {
        connections.remove(connection);
    }

    public void removeConnections(Collection<Connection> connections) {
        this.connections.removeAll(connections);
    }

    /**
     * Serialize pipeline into an RDF.
     *
     * @return
     */
    public List<Statement> serialize() {
        final List<Statement> result = new ArrayList<>(512);
        final ValueFactory vf = SimpleValueFactory.getInstance();
        final String iriBase = "http://localhost/pipelines/uv-";
        //
        final IRI pipeline = vf.createIRI(
                iriBase + UUID.randomUUID().toString());
        result.add(vf.createStatement(pipeline, RDF.TYPE,
                vf.createIRI("http://linkedpipes.com/ontology/Pipeline"),
                pipeline));
        result.add(vf.createStatement(pipeline,
                SKOS.PREF_LABEL,
                vf.createLiteral(label),
                pipeline));
        // Components.
        for (Component component : components) {
            component.iri = vf.createIRI(pipeline.stringValue()
                    + "/components/" + UUID.randomUUID().toString());
            result.add(vf.createStatement(component.iri, RDF.TYPE,
                    vf.createIRI("http://linkedpipes.com/ontology/Component"),
                    pipeline));
            result.add(vf.createStatement(component.iri,
                    SKOS.PREF_LABEL,
                    vf.createLiteral(component.label),
                    pipeline));
            if (component.description != null
                    && !component.description.isEmpty()) {
                result.add(vf.createStatement(component.iri,
                        DCTERMS.DESCRIPTION,
                        vf.createLiteral(component.description),
                        pipeline));
            }
            result.add(vf.createStatement(component.iri,
                    vf.createIRI("http://linkedpipes.com/ontology/x"),
                    vf.createLiteral(component.x),
                    pipeline));
            result.add(vf.createStatement(component.iri,
                    vf.createIRI("http://linkedpipes.com/ontology/y"),
                    vf.createLiteral(component.y),
                    pipeline));
            result.add(vf.createStatement(component.iri,
                    vf.createIRI("http://linkedpipes.com/ontology/template"),
                    vf.createIRI(component.template),
                    pipeline));

            // Configuration reference.
            if (!component.lpConfiguration.isEmpty()) {

                final IRI configGraph = vf.createIRI(component.iri.stringValue()
                        + "/configuration");

                result.add(vf.createStatement(component.iri,
                        vf.createIRI("http://linkedpipes.com/ontology/configurationGraph"),
                        configGraph,
                        pipeline));

                // Configuration graph.
                for (Statement statement : component.lpConfiguration) {
                    result.add(vf.createStatement(
                            statement.getSubject(),
                            statement.getPredicate(),
                            statement.getObject(),
                            configGraph));
                }

            }
        }
        // Connections.
        for (Connection connection : connections) {
            final IRI connectionIri = vf.createIRI(pipeline.stringValue()
                    + "/connection/" + UUID.randomUUID().toString());
            connection.write(vf, connectionIri, pipeline, result);
        }
        return result;
    }

    public static LpPipeline create(UvPipeline uv) {
        final LpPipeline lp = new LpPipeline();
        final Map<UvPipeline.Node, Component> componentMap = new HashMap<>();
        final ConfigurationLoader configLoader = new ConfigurationLoader();
        // Pipeline properties.
        lp.label = uv.getName();
        // Components.
        for (UvPipeline.Node node : uv.getGraph().getNodes()) {
            final Component lpComponent = new Component(
                    node.getDpuInstance().getName(),
                    node.getDpuInstance().getDescription(),
                    node.getPosition().getX(),
                    node.getPosition().getY());
            lpComponent.uvConfiguration = configLoader.loadConfiguration(
                    node.getDpuInstance().getUsedConfig());
            componentMap.put(node, lpComponent);
            lp.components.add(lpComponent);
        }
        // Connections.
        for (UvPipeline.Edge edge : uv.getGraph().getEdges()) {
            for (String cmd : edge.getScript().split(";")) {
                if (cmd.contains("run_after")) {
                    lp.connections.add(new RunAfter(
                            componentMap.get(edge.getFrom()),
                            componentMap.get(edge.getTo())));
                } else {
                    final String[] addr = cmd.split("->");
                    final String source = addr[0].trim();
                    final String target = addr[1].trim();
                    lp.connections.add(new DataConnection(
                            componentMap.get(edge.getFrom()), source,
                            componentMap.get(edge.getTo()), target));
                }
            }
        }
        // Convert pipeline.
        for (;;) {
            boolean update = false;
            for (Component component : lp.components) {
                if (!component.updated) {
                    update = true;
                    component.updated = true;
                    // If configuration is missing the component
                    // isnewly created LP componen.
                    if (component.uvConfiguration != null) {
                        component.uvConfiguration.update(lp, component);
                    }
                    break;
                }
            }
            if (!update) {
                break;
            }
        }
        return lp;
    }

}
