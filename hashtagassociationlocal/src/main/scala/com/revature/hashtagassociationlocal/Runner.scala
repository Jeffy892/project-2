package com.revature.hashtagassociationlocal

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions
import org.apache.spark.sql.DataFrameReader
import org.apache.http.impl.client.HttpClients
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.utils.URIBuilder
import org.apache.http.client.methods.HttpGet
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Paths
import scala.concurrent.Future

object Runner {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Hello Spark SQL")
      .master("local[4]")
      .getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("WARN")

    hashtagassociationlocal(spark)

  }

  def hashtagassociationlocal(spark: SparkSession) : Unit = {
    import spark.implicits._

    val bearerToken = System.getenv(("TWITTER_BEARER_TOKEN"))

    import scala.concurrent.ExecutionContext.Implicits.global

    val staticDf = spark.read.json("file:///home/clair/project-2/hashtagassociationlocal/twitterstream/tweetstream-1613077843475-1")

    staticDf.printSchema()

    // val streamDf = spark.readStream.schema(staticDf.schema).json("twitterstream")

    // println(staticDf.select("data.text").head())

    // val pattern = ".* +#[Bb][Tt][Ss] +(#[a-zA-Z0-9]+) +.*".r

    val pattern = ".* +(#[a-zA-Z0-9]+) +#[Bb][Tt][Ss] +.*".r

    staticDf
      .select("data.text")
      .as[String]
      .flatMap(text => {text match {
        case pattern(handle) => {Some(handle)}
        case notFound => None
      }})
      .groupBy("value")
      .count()
//      .sort(functions.desc("count"))
      .show()
//      .writeStream
//      .outputMode("complete")
//      .format("console")
//      .start()
//      .awaitTermination()
//      */

  }

}