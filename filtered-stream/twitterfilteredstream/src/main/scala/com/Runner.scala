import java.io.FileWriter
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import twitter4j._

// case class Tweet(
//   lang: String,
//   text: String
// )
object run {

  val bts = 1409798257L;

  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey(System.getenv("TWITTER_CONSUMER_KEY"))
    .setOAuthConsumerSecret(System.getenv("TWITTER_CONSUMER_SECRET"))
    .setOAuthAccessToken(System.getenv("TWITTER_ACCESS_TOKEN"))
    .setOAuthAccessTokenSecret(System.getenv("TWITTER_ACCESS_TOKEN_SECRET"))
    .build

  def main(args: Array[String]) {
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
    };
    twitterStream.cleanUp
    twitterStream.shutdown
  }

  def statuslistener = new StatusListener() {
    def onStatus(status: Status) {
      // println(s"${status.getLang}\t${status.getText}")
      printToFile(status, "tweets/bts.json")

    }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}

    def printToFile(status: Status, file: String) {
      val filewriter = new FileWriter(file, true)
      //val gson = new GsonBuilder().create()
      //val tweet = Tweet(status.getLang(), status.getText())
      //gson.toJson(tweet, filewriter)
      filewriter.write(s"${status.getLang}\n")
      filewriter.flush()
      filewriter.close()
      println("------------------------")
    }

    //TODO: Aggregate data en: 5, ko: 2, vi: 2, etc
    //Use Spark dataframes/datasets
  }
}
