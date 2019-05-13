# CS 498 - MP5 Templates

## Python codebase 

This repository contains templates to help you get started with MP5.

# ML vs MLLib
- Parts B and D (MLLib exercises) can be solved using either the
Dataframe-based API (pyspark.ml) or the RDD-based API (pyspark.mllib).
The corresponding templates for each have the suffix `_ml` and `_mllib`.
Make sure you rename the python files corresopnding to parts B and D to 
`part_b.py` and `part_d.py` respectively before submitting them.

# Execution instructions
- Each file can be executed by running
```spark-submit --packages graphframes:graphframes:0.7.0-spark2.4-s_2.11 part_xxx.py```
- You can alternatively run the following to get rid of spark logs
```spark-submit --packages graphframes:graphframes:0.7.0-spark2.4-s_2.11 part_xxx.py 2> /dev/null```
- Make sure that you have the given dataset in the directory you are running
the given code from. The structure this repository is arranged in is recommended.
- While the extra argument for graphframes is not required for part b
and part d, it is not necessary to remove it these parts


# Notes
* park_b_ml.py and part_d_ml.py - TODO :Using the new spark.ml package
*  https://piazza.com/class/jqz0r68mx9863m?cid=682
* Datacamp on PySpark - https://www.datacamp.com/courses/introduction-to-pyspark.
    * My notes about this course attached PySparkDatacamp.pdf
* Graphframes https://graphframes.github.io/graphframes/docs/_site/index.html
    * Graphframes quickstart https://graphframes.github.io/graphframes/docs/_site/quick-start.html
* More on Graphframes: https://docs.databricks.com/spark/latest/graph-analysis/graphframes/user-guide-python.html
* MLLib: https://spark.apache.org/docs/latest/ml-guide.html
* Reading data into [py]spark: https://towardsdatascience.com/a-brief-introduction-to-pyspark-ff428470187
    * documentation on createDataFrame function: http://spark.apache.org/docs/2.1.0/api/python/pyspark.sql.html#pyspark.sql.SparkSession.createDataFrame
* The databricks community edition allows you to run your code in a notebook: https://community.cloud.databricks.com
    * Install package graphframes in the Cluster -> Libraries area with Type Pypl


https://spark.apache.org/docs/latest/graphx-programming-guide.html#connected-components
https://medium.com/tensorist/using-k-means-to-analyse-hacking-attacks-81957c492c93
https://www.datascience.com/blog/graph-computations-apache-spark

spark-submit --packages graphframes:graphframes:0.7.0-spark2.4-s_2.11 part_c.py 2> /dev/null

http://billchambers.me/tutorials/2014/12/06/getting-started-with-apache-spark.html
