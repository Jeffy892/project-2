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
      
    val spark = SparkSession
        .builder()
        .appName("Hello Spark SQL")
        .master("local[4]")
        .getOrCreate()
      
    import spark.implicits._

    spark.sparkContext.setLogLevel("WARN")

    //tweetSparkSql(spark)

    //tweetMoreSparkSql.run(spark)

    json2csv.run(spark)

    }

    def tweetSparkSql(spark: SparkSession): Unit = {

        import spark.implicits._

        val df = spark.read.option("multiline", "true").json("BTS_geoOnly_210216_5am-6am_CDT.json")

        df.show()

        df.printSchema()

        df.select(countDistinct("data.id")).show()

        df.select(countDistinct("data.created_at")).show()

        df.select(countDistinct("includes.places.full_name")).show()

        df.groupBy("includes.places.full_name").count().show() 
        
        val windowGeo  = Window.partitionBy("includes.places.full_name").orderBy("data.created_at")
        df.withColumn("row_number", row_number.over(windowGeo)).show()

        val dfgeo = df.groupBy($"includes.places.full_name")
        .agg(
            functions.count($"includes.places.full_name").as("counts"),
            functions.avg($"data.public_metrics.retweet_count").as("avg_Retweet"),
            functions.avg($"data.public_metrics.reply_count").as("avg_Replies"),
            functions.avg($"data.public_metrics.like_count").as("avg_Like"),
            functions.avg($"data.public_metrics.quote_count").as("avg_Quotes")
        )
        .sort($"counts".desc)

        dfgeo.show(1000, false)

        dfgeo.explain(true)

        println(dfgeo.rdd.toDebugString)

        val w = Window.orderBy($"counts".desc)
        val result = dfgeo.withColumn("index", row_number().over(w))

        result.show(1000, false)
        result.repartition(1).write.format("json").save("ana_BTS_geoOnly_210216_morning_CDT_json")

    }

}

