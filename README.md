# uv2etl
Transformation of [UnifiedViews](http://unifiedviews.eu) pipelines to [LinkedPipes ETL](http://etl.linkedpipes.com) pipelines.

Input is the ZIP file pipeline export from UnifiedViews, output is the JSON-LD file containing LP-ETL pipeline that can be uploaded.

The output file name is optional, if not specified, the input filename is used and the ```.jsonld``` extension is appended.

Usage: ```java -jar uv2etl.jar -i {UV_PIPELINE_ZIP} [-o {LP_PIPELINE_JSON}]```

Note: The transformation needs to be implemented for each UV DPU separately. 
Right now, we have a majority of [Core DPUs for RDF transformations](https://github.com/UnifiedViews/Plugins) done.
If a DPU is present in the UV export for which the transformation is not implemented yet, it will be omitted in the LP-ETL pipeline.
In that case, either raise a Transformation request issue, or even better, create a pull request for that DPU.
