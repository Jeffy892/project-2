### Project 2: Twitter Analysis

#### Technologies used
- Twitter API
- Scala 2.11.12
- Spark 2.4.7
- Spark SQL
- SBT Assembly
- SBT

#### How to run this jar file:
- `Type spark-submit target/scala-2.11/project2-assembly-0.1.0-SNAPSHOT.jar \[option\]` Where options are  
    - `run` : it will run spark streaming
    - `read` : it will read the data from twitterstream folder
    - `popularArtist` : it will run a filter to twitter stream and create a json file listing all popular tags
    - `popularCount` : it will count the number of tweets per location from twitterstream-geo
    - `trending` : it will filter and count all the retweets for each tags.

#### Responsibilities
- Learn and use the Twitter API
- Collect information from tweets using Twitter API and Spark Streaming
- Write SparkSQL code to analyze the twitter data to answer the questions
- Managed the team's project github repo
- Wrote curl commands that request specific tweets with enough information using the twitter API
- Created google slides that explains the results of my analysis