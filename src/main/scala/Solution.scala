import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, last, when}
import org.apache.spark.sql.types.IntegerType

object Solution {
  def solve(sparkSession: SparkSession, inputDirectory: String, outputDirectory: String): Unit = {

    val input = sparkSession.read.option("header", value = false).csv(inputDirectory)
      .toDF()
      .withColumnRenamed("_c0", "number")
      .withColumnRenamed("_c1", "company")
      .withColumnRenamed("_c2", "value")
      .withColumn("number", col("number")
        .cast(IntegerType))
      .withColumn("value", col("value")
        .cast(IntegerType))

    val window = Window.partitionBy("company")
      .orderBy("number")
      .rangeBetween(Window.unboundedPreceding, Window.currentRow - 1)

    val result = input
      .withColumn(
        "prevNumber",
        last(
          when(col("value") > 1000, col("number")),
          ignoreNulls = true
        )
          .over(window)
      )
      .withColumn(
        "prevValue",
        last(when(col("value") > 1000, col("value")),
          ignoreNulls = true
        )
          .over(window)
      )
      .withColumn("prevNumber", col("prevNumber")
        .cast(IntegerType))
      .withColumn("prevValue", col("prevValue")
        .cast(IntegerType))

    val output = result
      .withColumn(
        "prevValue",
        when(col("prevNumber").isNull, 0)
          .otherwise(col("prevValue"))
      )
      .withColumn(
        "prevNumber",
        when(col("prevNumber").isNull, 0)
          .otherwise(col("prevNumber"))
      )

    output.orderBy("number").write.csv(outputDirectory)
  }
}
