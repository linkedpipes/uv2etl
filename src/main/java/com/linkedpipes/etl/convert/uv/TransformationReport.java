package com.linkedpipes.etl.convert.uv;

import com.linkedpipes.etl.convert.uv.pipeline.LpPipeline;

/**
 * Collect and print important information about transformation.
 *
 * TODO: At the end use to generate summary, or report that can be
 * used to create an issue.
 *
 * @author Petr Škoda
 */
public class TransformationReport {

    private static final TransformationReport SINGLETON
            = new TransformationReport();

    protected TransformationReport() {
    }

    public static TransformationReport getInstance() {
        return SINGLETON;
    }

    public void unknownComponents(LpPipeline.Component component) {

    }

    public void unknownConfiguration(String className) {

    }

    public void unsupportedComponents(LpPipeline.Component component,
            Object configuration) {

    }

}
