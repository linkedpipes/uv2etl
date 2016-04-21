package com.linkedpipes.etl.convert.uv.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Petr Škoda
 */
@XStreamAlias("MasterConfigObject")
class MasterConfigObject {

    private Map<String, String> configurations = new HashMap<>();

    public Map<String, String> getConfigurations() {
        return configurations;
    }

}
