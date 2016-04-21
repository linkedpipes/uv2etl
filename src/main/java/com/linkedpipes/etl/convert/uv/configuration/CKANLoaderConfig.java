package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("cz.opendata.unifiedviews.dpus.ckan.CKANLoaderConfig")
class CKANLoaderConfig implements Configuration {

    public enum Licenses {
        pddl {
            //Open Data Commons Public Domain Dedication and License (PDDL)
            //http://opendefinition.org/licenses/odc-pddl
            @Override
            public String toString() {
                return "odc-pddl";
            }
        },
        ccby {
            //Creative Commons Attribution
            //http://opendefinition.org/licenses/cc-by
            @Override
            public String toString() {
                return "cc-by";
            }
        },
        ccbysa {
            //Creative Commons Attribution Share-Alike
            //http://opendefinition.org/licenses/cc-by-sa
            @Override
            public String toString() {
                return "cc-by-sa";
            }
        },
        cczero {
            //Creative Commons CCZero
            //http://opendefinition.org/licenses/cc-zero
            @Override
            public String toString() {
                return "cc-zero";
            }
        },
        ccnc {
            //Creative Commons Non-Commercial (Any)
            @Override
            public String toString() {
                return "cc-nc";
            }
        },
        gfdl {
            //GNU Free Documentation License
            @Override
            public String toString() {
                return "cc-nc";
            }
        },
        notspecified {
            //License Not Specified
            @Override
            public String toString() {
                return "cc-nc";
            }
        },
        odcby {
            //Open Data Commons Attribution License
            //http://opendefinition.org/licenses/odc-by
            @Override
            public String toString() {
                return "odc-by";
            }
        },
        odcodbl {
            //Open Data Commons Open Database License (ODbL)
            //http://www.opendefinition.org/licenses/odc-odbl
            @Override
            public String toString() {
                return "odc-odbl";
            }
        },
        otherat {
            //Other (Attribution)
            @Override
            public String toString() {
                return "other-at";
            }
        },
        othernc {
            //Other (Non-Commercial)
            @Override
            public String toString() {
                return "other-nc";
            }
        },
        otherclosed {
            //Other (Not Open)
            @Override
            public String toString() {
                return "other-closed";
            }
        },
        otheropen {
            //Other (Open)
            @Override
            public String toString() {
                return "other-open";
            }
        },
        otherpd {
            //Other (Public Domain)
            @Override
            public String toString() {
                return "other-pd";
            }
        },
        ukogl {
            //UK Open Government Licence (OGL)
            //http://reference.data.gov.uk/id/open-government-licence
            @Override
            public String toString() {
                return "uk-ogl";
            }
        }
    }

    private static final Logger LOG
            = LoggerFactory.getLogger(CKANLoaderConfig.class);

    private String apiUri = "http://ckan.opendata.cz/api/rest/dataset";

    private String apiKey = "";

    private boolean loadToCKAN = true;

    private String datasetID = "";

    private String filename = "ckan-api.json";

    private String orgID = "d2664e4e-25ba-4dcc-a842-dcc5f2d2f326";

    @Override
    public void update(LpPipeline pipeline, LpPipeline.Component component) {

        // @DataUnit.AsOutput(name = "JSON")
        // @DataUnit.AsInput(name = "metadata")

        LOG.error("{} : Removed.", component);
        pipeline.removeComponent(component);
    }

}
