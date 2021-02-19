package com.revature.project2

import org.apache.spark.sql.SparkSession

object Runner {

    def main(args: Array[String]) : Unit = {

        if(args.length != 1) {
            println("Please select one of the following actions:")
            println("*******************************************")
            println("run : it will run spark streaming")
            println("read : it will read the data from twitterstream folder")
            println("popularArtist : it will run a filter to twitter stream and create a json file listing all popular tags")
            println("popularCount : it will count the number of tweets per location from twitterstream-geo")
            println("trending : it will filter and count all the retweets for each tags.")
            System.exit(1)
        }

        // Initialize a SparkSession
        val spark = SparkSession
            .builder()
            .appName("Project 2 Twitter Analysis")
            .master("local[4]")
            .getOrCreate()

        // Import implicits to remove some warns/errors
        import spark.implicits._

        spark.sparkContext.setLogLevel("ERROR")

        if(args(0) == "run") TwitterStream.run(spark)
        else if(args(0) == "read") TwitterStream.readData(spark)
        else if(args(0) == "popularArtist") TwitterStream.popularKpopAnalysis(spark)
        else if(args(0) == "popularCount") TwitterStream.countriesPopular(spark)
        else if(args(0) == "trending") TwitterStream.trendingKpopArtist(spark)
    }
}