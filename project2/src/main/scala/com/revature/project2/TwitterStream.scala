package com.revature.project2

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

object TwitterStream {

    def run(spark: SparkSession) : Unit = {
        import spark.implicits._

        val bearerToken = System.getenv(("TWITTER_BEARER_TOKEN"))

        import scala.concurrent.ExecutionContext.Implicits.global

        Future {
            tweetStreamToDir(bearerToken, queryString = "?tweet.fields=geo,lang,public_metrics,created_at&expansions=geo.place_id")
        }


        var start = System.currentTimeMillis()

        var filesFoundInDir = false

        while(!filesFoundInDir && (System.currentTimeMillis()-start) < 30000) {
            filesFoundInDir = Files.list(Paths.get("twitterstream")).findFirst().isPresent()
            Thread.sleep(100)
        }

        if(!filesFoundInDir) {
            println("Error: Unable to populate tweetstream after 30 seconds. Exiting..")
            System.exit(1)
        }

        val staticDf = spark.read.json("twitterstream")

        staticDf.printSchema()


    }

    def tweetStreamToDir(
        bearerToken: String,
        dirname: String = "twitterstream",
        linesPerFile: Int = 1000,
        queryString: String = ""
    ) = {
        
        val httpClient = HttpClients.custom.setDefaultRequestConfig(
            RequestConfig.custom.setCookieSpec(CookieSpecs.STANDARD).build()
        ).build()
        
        val uriBuilder: URIBuilder = new URIBuilder(
            s"https://api.twitter.com/2/tweets/sample/stream$queryString"
        )

        val httpGet = new HttpGet(uriBuilder.build())
        
        httpGet.setHeader("Authorization", s"Bearer $bearerToken")

        val response = httpClient.execute(httpGet)
        val entity = response.getEntity()

        if(null != entity) {
            val reader = new BufferedReader(
                new InputStreamReader(entity.getContent())
            )

            var line = reader.readLine()
            var fileWriter = new PrintWriter(Paths.get("tweetstream.tmp").toFile)
            var lineNumber = 1
            val millis = System.currentTimeMillis()
            while (line != null) {
                if(lineNumber % linesPerFile == 0) {
                    fileWriter.close()
                    Files.move(
                        Paths.get("tweetstream.tmp"),
                        Paths.get(s"$dirname/tweetstream-$millis-${lineNumber/linesPerFile}")
                    )
                    fileWriter = new PrintWriter(Paths.get("tweetstream.tmp").toFile)
                }
                fileWriter.print(line)
                line = reader.readLine()
                lineNumber += 1
            }
        }
    }
 
    
    def readData(spark: SparkSession) : Unit = {
        import spark.implicits._

        val staticDf = spark.read.json("twitterstream")

        staticDf.printSchema()
                //streamDf is a stream, using *Structured Streaming*
        //val streamDf = spark.readStream.schema(staticDf.schema).json("twitterstream")

        val df = spark.read.json("twitterstream")
        
        staticDf
            .filter(!functions.isnull($"includes.places"))
            .select(
                ($"includes.places.full_name").as("Place"),
                ($"data.text").as("Tweet"), 
                ($"data.lang").as("Language"), 
                ($"data.public_metrics.retweet_count").as("Retweet"),
                ($"data.public_metrics.reply_count").as("Replies"),
                ($"data.public_metrics.like_count").as("Likes"),
                ($"data.public_metrics.quote_count").as("Quotes"),
                ($"data.created_at").as("Created At")
            )
            .write
            .json("tweetstream-geo")


            /*.writeStream
            .outputMode("append")
            .format("console")
            .option("truncate", false)
            .start()
            .awaitTermination()*/

    }

}
