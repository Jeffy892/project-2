package com.revature.project2

import org.apache.spark.sql.SparkSession

object Runner {

    def main(args: Array[String]) : Unit = {

        // Initialize a SparkSession
        val spark = SparkSession
            .builder()
            .appName("Project 2 Twitter Analysis")
            .master("local[4]")
            .getOrCreate()

        // Import implicits to remove some warns/errors
        import spark.implicits._


        spark.sparkContext.setLogLevel("WARN")

        //TwitterStream.run(spark)
        TwitterStream.readData(spark)
    }
}