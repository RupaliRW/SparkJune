package org.itc.com


import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object SparkHiveDemo extends App{

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "Dataframe Demo")
 // sparkConf.set("spark.master", "local[*]")

  val spark = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()

  import spark.implicits._

  val empDF = Seq(("Rupali", "Mumbai"), ("Mohsin", "pune"), ("Devi", "bangalore"), ("Alex", "hyderabad"))
    .toDF("fName", "city")

  empDF.repartition(1).write.mode(SaveMode.Overwrite).saveAsTable("june.Employee")

}
