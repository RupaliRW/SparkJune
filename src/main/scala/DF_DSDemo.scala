package org.itc.com

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object DF_DSDemo extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "Dataframe Demo")
  sparkConf.set("spark.master", "local[*]")

  val spark = SparkSession.builder().config(sparkConf).getOrCreate()


  val ordersdf = spark.read.option("header", true).option("inferSchema",true).
    csv("D:\\Demos\\input\\orders.csv")


  case class orderdata (order_id:Int,order_date:String, order_customer_id:Int, order_status:String)
  import spark.implicits._
  val orderDS = ordersdf.as[orderdata]

  orderDS.filter("orderid <10").show(5)

}
