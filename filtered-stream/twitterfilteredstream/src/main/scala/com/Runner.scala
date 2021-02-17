import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrameReader
import org.apache.spark.sql._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

import java.io.FileWriter
import scala.concurrent.Future
import twitter4j._
import org.apache.spark.sql.types.StructType

object run {

  val bts = 1409798257L;

  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey(System.getenv("TWITTER_CONSUMER_KEY"))
    .setOAuthConsumerSecret(System.getenv("TWITTER_CONSUMER_SECRET"))
    .setOAuthAccessToken(System.getenv("TWITTER_ACCESS_TOKEN"))
    .setOAuthAccessTokenSecret(System.getenv("TWITTER_ACCESS_TOKEN_SECRET"))
    .build

  def main(args: Array[String]) {
    val spark = SparkSession
      .builder()
      .appName("btslangcounter")
      .master("local[4]")
      .getOrCreate()

    import spark.implicits._
    spark.sparkContext.setLogLevel("WARN")

    // streamTweets()
    // langCount(spark)
    getLang(spark)

    def statuslistener = new StatusListener() {
      def onStatus(status: Status) {
        printToFile(status, "tweets/bts.csv")

      }
      def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
      def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
      def onException(ex: Exception) { ex.printStackTrace }
      def onScrubGeo(arg0: Long, arg1: Long) {}
      def onStallWarning(warning: StallWarning) {}

      def printToFile(status: Status, file: String) {
        val filewriter = new FileWriter(file, true)
        println("filewriter start")
        filewriter.write(s"${status.getLang}\n")
        filewriter.flush()
        filewriter.close()
        println("------------------------")
      }
    }

    def streamTweets(): Unit = {
      val twitterStream = new TwitterStreamFactory(config).getInstance
      val query = new FilterQuery()
      query
        .follow(bts)
        .track("BTS")
      twitterStream.addListener(statuslistener)
      twitterStream.filter(query)
      val start = System.currentTimeMillis();
      while (System.currentTimeMillis() - start < 10 * 1000) {
        /* wait for x seconds */
      }
      twitterStream.cleanUp
      twitterStream.shutdown
    }

    def langCount(spark: SparkSession): Unit = {
      import spark.implicits._
      val df = spark.read
        .csv("tweets/bts.csv")
        .withColumnRenamed("_c0", "lang")

      println("langCount df schema")
      df.printSchema()
      df.show(false)

      println("count number of languages")
      val countLangs = df.select(countDistinct("lang")).show(false)

      println("count per language")
      val countPerLang =
        df.groupBy("lang").count.sort(desc("count")).show(false)

      df.createOrReplaceTempView("languages")

      println("total tweets")
      val numTweets = spark.sql("SELECT COUNT(lang) FROM languages").show(false)

      println("language count / total tweets = ratio")
      val ratio = spark
        .sql(
          "SELECT lang, COUNT(lang) as count, (COUNT(lang) / (SELECT count(lang) FROM languages as total)) as lang_to_total_ratio FROM languages GROUP BY lang ORDER BY count desc"
        )
        .show(false)
    }
    //get languages from json sample data
    def getLang(spark: SparkSession): Unit = {
      import spark.implicits._

      val df = spark.read
        .json("tweets/bts.json")
        .select("data.lang")

      println("getLang df schema")
      df.printSchema()
      df.show()

      println("Sample Data Analysis")
      df.createOrReplaceTempView("languages")

      println("total tweets about BTS")
      val numTweets = spark.sql("SELECT COUNT(lang) as total_tweets FROM languages").show(false)

      println("count per language")
      val countPerLang = spark.sql("SELECT COUNT(lang) as count_per_lang FROM languages GROUP BY lang")

      println("language count / total tweets = ratio")
      val ratio = spark
        .sql(
          "SELECT lang, COUNT(lang) as count, (COUNT(lang) / (SELECT count(lang) FROM languages as total)) as lang_to_total_ratio FROM languages GROUP BY lang ORDER BY count desc"
        )
        .show(false)

    }
  }
}
