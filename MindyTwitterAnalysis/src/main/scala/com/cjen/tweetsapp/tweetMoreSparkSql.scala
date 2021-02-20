package tweetsapp

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions
import org.apache.spark.sql.DataFrameReader
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Paths

object tweetMoreSparkSql {
    def run(spark: SparkSession, filename: String, dirname: String): Unit = {
        import spark.implicits._

        val df = spark.read.option("multiline", "true").json(filename)

        df.show()

        df.printSchema()

        val dfgeo = df.select(
            functions.to_timestamp($"data.created_at").as("Time"),
            functions.to_date($"data.created_at").as("Date"),
            functions.hour($"data.created_at").as("Hr"),
            functions.minute($"data.created_at").as("Min"),
            functions.second($"data.created_at").as("Sec"),
            ($"includes.places.full_name").as("Place"),
            ($"data.lang").as("Language"), 
            ($"data.public_metrics.retweet_count").as("Retweets"),
            ($"data.public_metrics.reply_count").as("Replies"),
            ($"data.public_metrics.like_count").as("Likes"),
            ($"data.public_metrics.quote_count").as("Quotes"),
            ($"data.text").as("Tweet")
        )

        dfgeo.explain(true)

        println(dfgeo.rdd.toDebugString)

        val w  = Window.orderBy($"Time")
        val result = dfgeo.withColumn("index", row_number().over(w))

        result.filter(functions.length($"Tweet") > 0)
        .filter(result("Tweet").rlike("BTS|kpop")) 
        .show(1000, false) 

        result.filter(functions.length($"Tweet") > 0)
        .filter($"Tweet".rlike("BTS|kpop")) 
        .write.json(dirname)

    }
}
