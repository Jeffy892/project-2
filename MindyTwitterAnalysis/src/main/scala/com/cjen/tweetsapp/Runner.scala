package tweetsapp;

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions
import org.apache.spark.sql.DataFrameReader
import org.apache.spark.sql.functions.countDistinct
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number
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

        if(args.length != 3) {
            println("Options:")
            println("tweetSparkSql [input file] [output folder]")
            println("tweetMoreSparkSql [input folder/input file] [output folder]")
            println("json2csv [input folder/input file] [output folder]")
            System.exit(1)
        }

        val spark = SparkSession
        .builder()
        .appName("Hello Spark SQL")
        .master("local[4]")
        .getOrCreate()
      
        import spark.implicits._

        spark.sparkContext.setLogLevel("WARN")

        if(args(0) == "tweetSparkSql") tweetSparkSql.run(spark, args(1), args(2))
        else if(args(0) == "tweetMoreSparkSql") tweetMoreSparkSql.run(spark, args(1), args(2))
        else if(args(0) == "json2csv") json2csv.run(spark, args(1), args(2))
    
    }

}

