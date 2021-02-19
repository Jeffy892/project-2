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
import org.apache.http.entity.StringEntity;

object Runner {
  def main(args: Array[String]): Unit = {
      val spark = SparkSession
      .builder()
      .appName("project 2")
      .master("local[4]")
      .getOrCreate()

      import spark.implicits._

     spark.sparkContext.setLogLevel("WARN")

     tweetStream(spark)
  }

  def tweetStream(spark: SparkSession): Unit = {
    import spark.implicits._
    val bearerToken = System.getenv(("TWITTER_BEARER_TOKEN"))
    import scala.concurrent.ExecutionContext.Implicits.global
    
    Future {
      tweetStreamToDir(bearerToken)
    }
    var start = System.currentTimeMillis()
    var filesFoundInDir = false
    val TIMEOUT_IN_S = 60 * 5
    while(!filesFoundInDir && (System.currentTimeMillis()-start) < TIMEOUT_IN_S * 1000) {
      filesFoundInDir = Files.list(Paths.get("twitterstream")).findFirst().isPresent()
      Thread.sleep(500)
    }
    if(!filesFoundInDir) {
      println(s"Error: Unable to populate tweetstream after ${TIMEOUT_IN_S} seconds.  Exiting..")
      System.exit(1)
    }

    val staticDf = spark.read.json("twitterstream")

    staticDf.printSchema()

    val streamDf = spark.readStream.schema(staticDf.schema).json("twitterstream")
    streamDf
      .select($"data.text")
      .writeStream
      .outputMode("append")
      .format("console")
      .start()
      .awaitTermination()
  }

  def tweetStreamToDir(
      bearerToken: String,
      dirname: String = "twitterstream",
      linesPerFile: Int = 1000
  ) = {

    val httpClient = HttpClients.custom
      .setDefaultRequestConfig(
        RequestConfig.custom.setCookieSpec(CookieSpecs.STANDARD).build()
      )
      .build()
    val uriBuilder: URIBuilder = new URIBuilder(
      s"https://api.twitter.com/2/tweets/search/stream?tweet.fields=lang,geo,public_metrics,created_at&expansions=geo.place_id&place.fields=full_name"
    )
    val httpGet = new HttpGet(uriBuilder.build())
    httpGet.setHeader("Authorization", s"Bearer $bearerToken")
    
    val response = httpClient.execute(httpGet)
    val entity = response.getEntity()
    if (null != entity) {
      val reader = new BufferedReader(
        new InputStreamReader(entity.getContent())
      )
      var line = reader.readLine()
      var fileWriter = new PrintWriter(Paths.get("tweetstream.tmp").toFile)
      var lineNumber = 1
      val millis = System.currentTimeMillis() 
      while (line != null) {
        if (lineNumber % linesPerFile == 0) {
          fileWriter.close()
          Files.move(
            Paths.get("tweetstream.tmp"),
            Paths.get(s"$dirname/tweetstream-$millis-${lineNumber/linesPerFile}"))
          fileWriter = new PrintWriter(Paths.get("tweetstream.tmp").toFile)
          println(s"writing batch to file: ${lineNumber/linesPerFile}")
        }
        fileWriter.println(line)
        println(s"Received line ${lineNumber}: ${line.take(120)}")
        line = reader.readLine()
        lineNumber += 1
      }
    }
  }
}
