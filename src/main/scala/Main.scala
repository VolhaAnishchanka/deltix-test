import Configurations.{inputDirectory, outputDirectory, azureConfigs}
import org.apache.spark.sql.SparkSession

case object Main {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Deltix")
      .config("spark.master", "local")
      .getOrCreate()

    azureConfigs foreach { case (key, value) => spark.conf.set(key, value) }

    Solution.solve(spark, inputDirectory, outputDirectory)

    spark.stop()
  }
}
