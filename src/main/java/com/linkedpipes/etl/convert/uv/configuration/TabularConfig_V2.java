package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.openrdf.model.IRI;
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
@XStreamAlias("cz.cuni.mff.xrg.uv.transformer.tabular.TabularConfig_V2")
class TabularConfig_V2 implements Configuration {

    static final Logger LOG
            = LoggerFactory.getLogger(TabularConfig_V2.class);

    @XStreamAlias("cz.cuni.mff.xrg.uv.transformer.tabular.parser.ParserType")
    enum ParserType {
        CSV,
        DBF,
        XLS
    }

    @XStreamAlias("eu.unifiedviews.plugins.transformer.tabular.column.ColumnType")
    enum ColumnType {
        String,
        Integer,
        Long,
        Double,
        Float,
        Date,
        Boolean,
        gYear,
        Decimal,
        Auto
    }

    @XStreamAlias("cz.cuni.mff.xrg.uv.transformer.tabular.column.ColumnInfo_V1")
    static class ColumnInfo_V1 {

        String URI = null;

        ColumnType type = ColumnType.Auto;

        Boolean useTypeFromDfb = null;

        String language = null;

        ColumnInfo_V1() {
        }

        ColumnInfo_V1(String URI, ColumnType type) {
            this.URI = URI;
            this.type = type;
        }

        ColumnInfo_V1(String URI) {
            this.URI = URI;
        }

    }

    @XStreamAlias("cz.cuni.mff.xrg.uv.transformer.tabular.column.NamedCell_V1")
    static class NamedCell_V1 {

        String name = "A0";

        Integer rowNumber = 0;

        Integer columnNumber = 0;

        NamedCell_V1() {
        }

    }

    static class AdvanceMapping {

        String uri = "";

        String template = "";

        AdvanceMapping() {
        }

        AdvanceMapping(String uri, String template) {
            this.uri = uri;
            this.template = template;
        }

    }

    private String keyColumn = null;

    private String baseURI = "http://localhost";

    private Map<String, ColumnInfo_V1> columnsInfo = new LinkedHashMap<>();

    private List<AdvanceMapping> columnsInfoAdv = new LinkedList<>();

    private List<NamedCell_V1> namedCells = new LinkedList<>();

    private String quoteChar = "\"";

    private String delimiterChar = ",";

    private Integer linesToIgnore = 0;

    private String encoding = "UTF-8";

    private Integer rowsLimit = null;

    private ParserType tableType = ParserType.CSV;

    private boolean hasHeader = true;

    private boolean generateNew = true;

    private boolean ignoreBlankCells = false;

    private boolean advancedKeyColumn = false;

    private String rowsClass = "http://unifiedviews.eu/ontology/t-tabular/Row";

    private String xlsSheetName = null;

    private boolean staticRowCounter = false;

    private boolean generateRowTriple = true;

    private boolean useTableSubject = false;

    private boolean autoAsStrings = false;

    private boolean generateTableClass = false;

    private boolean generateLabels = false;

    private boolean stripHeader = false;

    private boolean dbfTrimString = false;

    private boolean xlsAdvancedDoubleParser = false;

    private boolean ignoreMissingColumn = false;

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        pipeline.renameInPort(component, "table", "InputFiles");
        pipeline.renameOutPort(component, "triplifiedTable", "OutputRdf");

        component.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-tabularUv");

        final ValueFactory vf = SimpleValueFactory.getInstance();
        final List<Statement> st = new ArrayList<>();
        Integer counter = 0;
        final IRI configResource = vf.createIRI("http://localhost/resources/configuration/t-tabularUv");
        st.add(vf.createStatement(
                configResource,
                RDF.TYPE,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#Configuration")));

        if (keyColumn != null) {
            st.add(vf.createStatement(
                    configResource,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#keyColumn"),
                    vf.createLiteral(keyColumn)));
        }

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#baseUri"),
                vf.createLiteral(baseURI)));

        // columnsInfo
        for (Map.Entry<String, ColumnInfo_V1> item : columnsInfo.entrySet()) {
            IRI iri = vf.createIRI("http://localhost/resources/configuration/t-tabularUv/column_" + (++counter));

            st.add(vf.createStatement(
                    configResource,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#column"),
                    iri));
            st.add(vf.createStatement(iri, RDF.TYPE,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#ColumnInfo")));

            final ColumnInfo_V1 itemValue = item.getValue();
            st.add(vf.createStatement(iri,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#name"),
                    vf.createLiteral(item.getKey())));

            st.add(vf.createStatement(iri,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#uri"),
                    vf.createLiteral(itemValue.URI)));
            st.add(vf.createStatement(iri,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#type"),
                    vf.createLiteral(itemValue.type.toString())));
            if (itemValue.useTypeFromDfb != null) {
                st.add(vf.createStatement(iri,
                        vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#typeFromDbf"),
                        vf.createLiteral(itemValue.useTypeFromDfb)));
            }
            if (itemValue.language != null) {
                st.add(vf.createStatement(iri,
                        vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#lang"),
                        vf.createLiteral(itemValue.language)));
            }

        }

        // columnsInfoAdv
        for (AdvanceMapping item : columnsInfoAdv) {
            IRI iri = vf.createIRI("http://localhost/resources/configuration/t-tabularUv/advanced_" + (++counter));

            st.add(vf.createStatement(configResource,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#advancedMapping"),
                    iri));
            st.add(vf.createStatement(iri, RDF.TYPE,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#AdvancedMapping")));

            st.add(vf.createStatement(iri,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#uri"),
                    vf.createLiteral(item.uri)));
            st.add(vf.createStatement(iri,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#template"),
                    vf.createLiteral(item.template)));
        }

        // namedCells
        for (NamedCell_V1 item : namedCells) {
            IRI iri = vf.createIRI("http://localhost/resources/configuration/t-tabularUv/named_" + (++counter));

            st.add(vf.createStatement(configResource,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#namedCell"),
                    iri));
            st.add(vf.createStatement(iri, RDF.TYPE,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#NamedCell")));

            st.add(vf.createStatement(iri,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#name"),
                    vf.createLiteral(item.name)));
            st.add(vf.createStatement(iri,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#rowNumber"),
                    vf.createLiteral(item.rowNumber)));
            st.add(vf.createStatement(iri,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#columnNumber"),
                    vf.createLiteral(item.columnNumber)));
        }

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#quote"),
                vf.createLiteral(quoteChar)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#delimeter"),
                vf.createLiteral(delimiterChar)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#linesToIgnore"),
                vf.createLiteral(linesToIgnore)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#encoding"),
                vf.createLiteral(encoding)));

        if (rowsLimit != null) {
            st.add(vf.createStatement(
                    configResource,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#rowsLimit"),
                    vf.createLiteral(rowsLimit)));
        }

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#tableType"),
                vf.createLiteral(tableType.toString())));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#hasHeader"),
                vf.createLiteral(hasHeader)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#generateNew"),
                vf.createLiteral(generateNew)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#ignoreBlankCell"),
                vf.createLiteral(ignoreBlankCells)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#advancedKey"),
                vf.createLiteral(advancedKeyColumn)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#rowsClass"),
                vf.createLiteral(rowsClass)));

        if (xlsSheetName != null) {
            st.add(vf.createStatement(
                    configResource,
                    vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#xlsSheetName"),
                    vf.createLiteral(xlsSheetName)));
        }

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#staticRowCounter"),
                vf.createLiteral(staticRowCounter)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#generateRowTriple"),
                vf.createLiteral(generateRowTriple)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#useTableSubject"),
                vf.createLiteral(useTableSubject)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#autoAsStrings"),
                vf.createLiteral(autoAsStrings)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#generateTableClass"),
                vf.createLiteral(generateTableClass)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#generateLabels"),
                vf.createLiteral(generateLabels)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#stripHeader"),
                vf.createLiteral(stripHeader)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#dbfTrimString"),
                vf.createLiteral(dbfTrimString)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#xlsAdvancedDoubleParser"),
                vf.createLiteral(xlsAdvancedDoubleParser)));

        st.add(vf.createStatement(
                configResource,
                vf.createIRI("http://plugins.linkedpipes.com/ontology/t-tabularUv#ignoreMissingColumn"),
                vf.createLiteral(ignoreMissingColumn)));

        component.setLpConfiguration(st);

        // Insert t-graphMerger and reconnect edges.
        final LpPipeline.Component merger = new LpPipeline.Component(
                "rdf-graph-merger",
                "[I]",
                component.getX(),
                component.getY() + 40);

        merger.setTemplate(LpPipeline.BASE_IRI + "resources/components/t-graphMerger");

        pipeline.insertComponent(merger, 0, 60);
        pipeline.reconnectOutput(component, "OutputRdf", merger, "OutputRdf");
        pipeline.addDataConnection(component, "OutputRdf", merger, "InputRdf");

    }

}
