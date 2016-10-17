/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkedpipes.etl.convert.uv.configuration;

import com.linkedpipes.etl.convert.uv.pipeline.UvPipeline;

/**
 *
 * @author Petr Škoda
 */
public class ConfigurationFromJarFile {

    private ConfigurationFromJarFile() {
    }

    public static Configuration resolve(UvPipeline.Instance instance) {
        String jarFile = instance.getTemplate().getJarName();
        if (jarFile.contains("t-rdfMerger")) {
            return new RdfMergerConfig_V1();
        } else if (jarFile.contains("t-unzipper")) {
            return new UnZipperConfig_V1();
        } else {
            throw new RuntimeException("Unknown jar-file: " + jarFile + " for "
                + instance.getName());
        }
    }

}
