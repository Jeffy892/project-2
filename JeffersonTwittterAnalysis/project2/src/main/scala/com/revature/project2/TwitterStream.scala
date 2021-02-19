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
import scala.collection.immutable.Map
import scala.collection.immutable.List
object TwitterStream {

    val usStates = Map(
        ("AL" -> "ALABAMA"),
        ("AK" -> "ALASKA"),
        ("AS" -> "AMERICAN SAMOA"),
        ("AZ" -> "ARIZONA"),
        ("AR" -> "ARKANSAS"),
        ("CA" -> "CALIFORNIA"),
        ("CO" -> "COLORADO"),
        ("CT" -> "CONNECTICUT"),
        ("DE" -> "DELAWARE"),
        ("DC" -> "DISTRICT OF COLUMBIA"),
        ("FL" -> "FLORIDA"),
        ("GA" -> "GEORGIA"),
        ("GU" -> "GUAM"),
        ("HI" -> "HAWAII"),
        ("ID" -> "IDAHO"),
        ("IL" -> "ILLINOIS"),
        ("IN" -> "INDIANA"),
        ("IA" -> "IOWA"),
        ("KS" -> "KANSAS"),
        ("KY" -> "KENTUCKY"),
        ("LA" -> "LOUISIANA"),
        ("ME" -> "MAINE"),
        ("MD" -> "MARYLAND"),
        ("MA" -> "MASSACHUSETTS"),
        ("MI" -> "MICHIGAN"),
        ("MN" -> "MINNESOTA"),
        ("MS" -> "MISSISSIPPI"),
        ("MO" -> "MISSOURI"),
        ("MT" -> "MONTANA"),
        ("NE" -> "NEBRASKA"),
        ("NV" -> "NEVADA"),
        ("NH" -> "NEW HAMPSHIRE"),
        ("NJ" -> "NEW JERSEY"),
        ("NM" -> "NEW MEXICO"),
        ("NY" -> "NEW YORK"),
        ("NC" -> "NORTH CAROLINA"),
        ("ND" -> "NORTH DAKOTA"),
        ("MP" -> "NORTHERN MARIANA IS"),
        ("OH" -> "OHIO"),
        ("OK" -> "OKLAHOMA"),
        ("OR" -> "OREGON"),
        ("PA" -> "PENNSYLVANIA"),
        ("PR" -> "PUERTO RICO"),
        ("RI" -> "RHODE ISLAND"),
        ("SC" -> "SOUTH CAROLINA"),
        ("SD" -> "SOUTH DAKOTA"),
        ("TN" -> "TENNESSEE"),
        ("TX" -> "TEXAS"),
        ("UT" -> "UTAH"),
        ("VT" -> "VERMONT"),
        ("VA" -> "VIRGINIA"),
        ("VI" -> "VIRGIN ISLANDS"),
        ("WA" -> "WASHINGTON"),
        ("WV" -> "WEST VIRGINIA"),
        ("WI" -> "WISCONSIN"),
        ("WY" -> "WYOMING")
    )

    val usList = List("AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "VI", "WA", "WV", "WI", "WY")

    def printStates() : Unit = {
        val list = usStates.toList
        println(list)
        println(list(1)._1)
    }

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
            Thread.sleep(500)
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
        val millis = System.currentTimeMillis()
        
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
            .json(s"tweetstream-geo-$millis")


            /*.writeStream
            .outputMode("append")
            .format("console")
            .option("truncate", false)
            .start()
            .awaitTermination()*/

    }

    /**
      * Function analyzes the twitterstream data provided to retrieve the most used # and @ in tweets
      * that contains #kpop, #k_pop, @kpop, @k_pop in the tweet text. 
      * 
      * Note: it does not filter the # or @ that has the kpop or k_pop (ex: #kpop, #k_pop, @kpop, @k_pop)
      *
      * @param spark
      */
    def popularKpopAnalysis(spark: SparkSession) : Unit = {
        import spark.implicits._

        val staticDf = spark.read.json("twitterstream")
        staticDf.printSchema()

        val millis = System.currentTimeMillis()
        val hashtags = ".*([#,@][a-zA-Z0-9]+).*".r
        staticDf
            .select(functions.split($"data.text", " "))
            .as[String]
            .flatMap(text => {
                text match {
                    case hashtags(handle) => Some{(handle)}
                    case notFound => None
                }
            })
            .groupBy("value")
            .count()
            .sort($"count".desc)
            .write
            .json(s"tweetstream-largest-tags-$millis")
            //.show()
    }

    /**
      * 
      *
      * @param spark
      */
    
    def trendingKpopArtist(spark: SparkSession) : Unit = {
        import spark.implicits._

        val staticDf = spark.read.json("twitterstream")
        staticDf.printSchema()

        val millis = System.currentTimeMillis()

        val hashtags = ".*([#,@][a-zA-Z0-9]+).*".r


        staticDf
            .filter($"data.text".contains("#kpop") || 
                    $"data.text".contains("#k_pop") ||
                    $"data.text".contains("@kpop") ||
                    $"data.text".contains("@k_pop")
            )
            .select($"data.text", $"data.public_metrics.retweet_count")
            .groupBy($"text")
            .sum()
            .sort($"sum(retweet_count)".desc)
            .write
            .json(s"tweetstream-topretweets-$millis")
    }

    /**
      * Analyzes data from the twitter stream data that returns the amount of kpop tweets in each region or are interms of country
      * or city
      *
      * Note: The twitterstream data does not guarantee that the tweets will contain the geo location of the tweet therefore some of the data
      * that does not have any geolocation to them will be filtered out the analysis.
      * 
      * @param spark
      */
    def countriesPopular(spark: SparkSession) : Unit = {
        import spark.implicits._

        val staticDf = spark.read.json("tweetstream-geo-1613586499053")
        staticDf.printSchema()

        val millis = System.currentTimeMillis()


        staticDf
            //.filter(staticDf("Language") === "en")
            //.filter($"Place".contains(usList.toDS()))
            //.filter($"Tweet".contains("#kpop") || 
            //         $"Tweet".contains("#k_pop") ||
            //         $"Tweet".contains("@kpop") ||
            //         $"Tweet".contains("@k_pop") ||
            //         $"Tweet".contains("#BTS") ||
            //         $"Tweet".contains("@BTS")
            // )
            .select(
                ("Place"),
                ("Language"),
                ("Created At")
                //($"data.public_metrics.retweet_count").as("retweet_count")
            )
            .groupBy("Place")
            .count()
            .sort($"count".desc)
            .write
            .json(s"tweetstream-kpopcountry-states-$millis")
            //.show()
    }


    
}
