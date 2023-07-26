package org.itc.com

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object WCInOneLine {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[1]","WordCountDemo")
    //RDD is a low level API
    val rdd1 = sc.textFile("D:/Demos/input/data.txt")

   val rdd_out = rdd1.flatMap(x => x.split(" ")).map(x => (x.toLowerCase(),1)).reduceByKey((x,y) => x+y).sortBy(_._2)

    rdd_out.collect.foreach(println)


  }
}