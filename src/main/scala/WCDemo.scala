package org.itc.com

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object WCDemo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    println("new changes")
    val sc = new SparkContext();


    //RDD is a low level API
    val rdd1 = sc.textFile(args(0))

    // input is a line , output as words, 1 to M
    val rdd2 = rdd1.flatMap(x => x.split(" "))
    //input is words - output word,1, generating key,value pair, 1 to 1
    val rdd3 = rdd2.map(x => (x,1))
    //do word count - aggregation
    val rdd4 =  rdd3.reduceByKey((x,y) => x+y)

    rdd4.collect.foreach(println)


  }
}