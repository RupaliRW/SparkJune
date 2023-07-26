package org.itc.com

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.storage.StorageLevel

object sparkSqlDemo extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "Dataframe Demo")
  sparkConf.set("spark.master", "local[*]")

  val spark = SparkSession.builder().config(sparkConf).getOrCreate()


  val ordersdf = spark.read.option("header", true).option("inferSchema",true).
    csv("D:\\Demos\\input\\orders.csv")

  ordersdf.cache()
  ordersdf.persist(StorageLevel.MEMORY_AND_DISK)

ordersdf.createOrReplaceTempView("order")
  spark.sql("select * from order where order_id<3").show()

}
