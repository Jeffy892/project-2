import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrameReader
import org.apache.spark.sql._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

import java.io.FileWriter
import scala.concurrent.Future
import twitter4j._
import org.apache.spark.sql.types.StructType
import java.nio.file.Paths
import java.nio.file.Files

object run {
  
  val OUTPUT_DIR = "tweets_output"
  val LINES_PER_FILE = 50
  val now = System.currentTimeMillis()
  var currentLineNumber = 1
  
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
    // langTweetStream(spark)
    
    // langCount(spark) //lang stats from tweets/bts.csv
    getLangStat(spark) //lang stats from tweets-sampled-data
  }

  def printToFile(status: Status, tempFile: String, outputDir: String) {
    val isFinishedFile = currentLineNumber % LINES_PER_FILE == 0
    if (isFinishedFile) {
      // move temp file into folder
      Files.move(
        Paths.get(tempFile),
        Paths.get(s"$outputDir/bts-${now}-${currentLineNumber / LINES_PER_FILE}")
        )
        println(s"/!\\ moving set of ${LINES_PER_FILE} tweets to folder")
      }
    val append = !isFinishedFile
    val filewriter = new FileWriter(tempFile, append)
    filewriter.write(s"${status.getLang}\n")
    filewriter.flush()
    filewriter.close()
    currentLineNumber += 1
    println("-------------")
  }

  def streamTweets(): Unit = {
    val twitterStream = new TwitterStreamFactory(config).getInstance
    val query = new FilterQuery()

    val statuslistener = new StatusListener() {
      def onStatus(status: Status) {
        printToFile(status, "bts.tmp", OUTPUT_DIR)
      }
      def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
      def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
      def onException(ex: Exception) { ex.printStackTrace }
      def onScrubGeo(arg0: Long, arg1: Long) {}
      def onStallWarning(warning: StallWarning) {}
    }

    query
      .follow(bts)
      .track("BTS")
    twitterStream.addListener(statuslistener)
    twitterStream.filter(query)
    val start = System.currentTimeMillis();
    while (System.currentTimeMillis() - start < 5 * 1000) {
      /* wait for x seconds */
    }
    twitterStream.cleanUp
    twitterStream.shutdown
  }

  def langTweetStream(spark: SparkSession): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    Future {
      streamTweets()
    }
    import spark.implicits._

    val TIMEOUT_IN_MS = 60 * 1000
    val start = System.currentTimeMillis()
    var filesFoundInDir = false
    while(!filesFoundInDir && (System.currentTimeMillis()-start) < TIMEOUT_IN_MS) {
      filesFoundInDir = Files.list(Paths.get(OUTPUT_DIR)).findFirst().isPresent()
      Thread.sleep(500)
    }
    if(!filesFoundInDir) {
      println(s"Error: Unable to populate ${OUTPUT_DIR} after ${TIMEOUT_IN_MS / 1000} seconds. Exiting.")
      System.exit(1)
    }

    val staticDf = spark.read
      .csv(OUTPUT_DIR)
      .withColumnRenamed("_c0", "lang")

    staticDf.printSchema()

    val streamDf = spark.readStream.schema(staticDf.schema).csv(OUTPUT_DIR)

    streamDf
      .select("lang")
      .groupBy("lang")
      .count()
      .sort(functions.desc("count"))
      .writeStream
      .outputMode("complete")
      .format("console")
      .start()
      .awaitTermination()
  }

  def langCount(spark: SparkSession): Unit = {
    import spark.implicits._
    val df = spark.read
      .csv(s"{$OUTPUT_DIR}/*")
      .withColumnRenamed("_c0", "lang")

    println("langCount df schema")
    df.printSchema()
    df.show(100, false)

    println("count number of languages")
    val countLangs = df.select(countDistinct("lang")).show(100, false)

    println("count per language")
    val countPerLang =
      df.groupBy("lang").count.sort(desc("count")).show(100, false)

    df.createOrReplaceTempView("languages")

    println("total tweets")
    val numTweets = spark.sql("SELECT COUNT(lang) FROM languages").show(false)

    println("language count / total tweets = ratio")
    val ratio = spark.sql(
      "SELECT lang, COUNT(lang) as count, (COUNT(lang) / (SELECT count(lang) FROM languages as total)) as lang_to_total_ratio FROM languages GROUP BY lang ORDER BY count desc"
    )
    ratio.show(100, false)
  }

  //get languages from json sample data
  def getLangStat(spark: SparkSession): Unit = {
    import spark.implicits._

    val df = spark.read
      .json("tweets-sampled-data/*")
      // .json("tweets-sampled-data/americas/*")
      // .json("tweets-sampled-data/asia/*")
      // .json("tweets-sampled-data/europe/*")
      // .json("tweets/bts.json") //test sample
      .select("data.lang")

    println("getLang df schema")
    df.printSchema()
    df.show()

    println("--------Sample Data Analysis--------")
    df.createOrReplaceTempView("languages")

    println("total tweets about BTS")
    val numTweets =
      spark.sql("SELECT COUNT(lang) as total_tweets FROM languages")
    numTweets.show(false)

    println("number of languages")
    val countLangs = df.select(countDistinct("lang"))
    countLangs.show(100, false)

    println("count per language")
    val countPerLang = spark.sql(
      "SELECT lang, COUNT(lang) as count_per_lang FROM languages GROUP BY lang ORDER BY count_per_lang desc"
    )
    countPerLang.show(100, false)

    println("language count / total tweets = ratio")
    val ratio = spark.sql(
      "SELECT lang, COUNT(lang) as count, (COUNT(lang) / (SELECT count(lang) FROM languages as total)) as lang_to_total_ratio FROM languages GROUP BY lang ORDER BY count desc"
    )
    ratio.show(100, false)
  }
}
