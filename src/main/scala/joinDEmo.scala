package org.itc.com

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.functions.broadcast
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.storage.StorageLevel

object joinDEmo extends App{

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "Dataframe Demo")
  sparkConf.set("spark.master", "local[*]")

  val spark = SparkSession.builder().config(sparkConf).getOrCreate()

  import spark.implicits._

  val empDF = Seq(("Rupali", "Mumbai"), ("Mohsin", "pune"), ("Devi", "bangalore"), ("Alex", "hyderabad"))
    .toDF("fName", "city")

  val citiesDF = Seq(("Mumbai", "India"), ("pune", "India"), ("bangalore", "India"), ("hyderabad", "India"))
    .toDF("city", "country")

 //empDF.join(citiesDF,empDF.col("city") === citiesDF.col("city"),"inner").show()

  //try join using spark sql

  //broadcast join
  val resDF = empDF.join(broadcast(citiesDF),empDF.col("city") === citiesDF.col("city"),"inner")
 val df1= resDF.drop(citiesDF.col("city"))
  df1.select("city","fname","country").show()


}
