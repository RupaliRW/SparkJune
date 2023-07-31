package org.itc.com


import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object DFDemo extends App{
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name","Dataframe Demo")
  sparkConf.set("spark.master","local[*]")

  val spark = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
  //way1 : DDL string
  val ordersDDL = "orderid Int, orderdate String, custid Int, status String"

  //way2 : programmatic
  val orderSchema = StructType(List(
    StructField("orderid",IntegerType,true),
    StructField("orderdate",StringType,true),
    StructField("custid",IntegerType,true),
    StructField("orderstatus",StringType,true)
  ))

  val ordersdf = spark.read.option("header",true).schema(orderSchema).
     csv("D:\\Demos\\input\\orders.csv")

  val df1 = ordersdf.where("custid > 10000")
    .groupBy("orderstatus").count()


  df1.show(5)

  //df1.repartition(1).write.csv("D:\\Demos\\output\\ordersOutput1")

  df1.repartition(1).write.format("csv").mode(SaveMode.Overwrite)
    .option("path","D:\\Demos\\output\\output2").save()


  df1.coalesce(1).write.format("csv").mode(SaveMode.Overwrite)
    .option("path", "D:\\Demos\\output\\output2").save()

}
