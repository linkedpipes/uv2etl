package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    private String rowsClass = "\"http://unifiedviews.eu/ontology/t-tabular/Row";

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

        if (null == tableType) {
            throw new RuntimeException("Null table type.");
        }

        pipeline.renameInPort(component, "table", "InputFiles");
        pipeline.renameOutPort(component, "triplifiedTable", "OutputRdf");

        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);

    }

}
