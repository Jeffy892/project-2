# Project-2 Group 3 Twitter Big Data Analysis

## Twitter data analysis proposal
We would like to explore questions about a popular Korean pop (“Kpop”) artist, BTS from Twitter, while also looking at the other Kpop groups and some information on other pop in America

## Specific Questions to Answer

- **Study how #BTS is distributed globally in order to answer two business questions as follows: (Mindy Jen)**

  * Q1: What countries would you suggest BTS to hold a series of global concert tours in 2022? 

  * Q2: What languages would you recommend BTS to speak in their global concert tours? 

## Resposibilities

-  Behave as the project manager to set up the project scope for each individual involved
-  Play an integral role to leverage team members' efforts 
-  Experiment curl commands used to request specific tweets containing necessary information using Twitter API
-  Make good use of Spark/Scala to collect information from tweets using Twitter API and Spark Streaming 
-  Develop SparkSQL codes used to create and execute queries related to our questions 
-  Analyze twitter data to answer questions through data science technologies and frameworks  
-  Create Google slides for the presentation 
-  Practice Presentation 

## Technologies

- Apache Spark
- Spark SQL
- YARN
- HDFS and/or S3
- Scala 2.12.10
- Twitter API
- [python 3.8.3](https://www.anaconda.com/products/individual)
- [pandas](https://www.anaconda.com/products/individual)
- [numpy](https://www.anaconda.com/products/individual)
- [matplotlib.pyplot](https://www.anaconda.com/products/individual)
- [seaborn](https://www.anaconda.com/products/individual)
- [jupyter notebook](https://www.anaconda.com/products/individual)
- PySpark (optional for project 2)
- Docker (optional for project 2)
- Scikit-learn (optional for project 2)
- Tensorflow or Pytorch (optional for project 2)
- Git + GitHub

## Twitter Data Query 

- [Ref.](https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/quick-start)
- Build the rule for the inquiry of filtered stream data via two different approaches below:
  * bash terminal window
  * [postman](https://developer.twitter.com/en/docs/tools-and-libraries/using-postman#:~:text=%20Getting%20started%20with%20Twitter's%20Postman%20collections%20,to%20choose%20an%20endpoint%20from%20the...%20More) 

        curl -X POST 'https://api.twitter.com/2/tweets/search/stream/rules' \
        -H "Content-type: application/json" \
        -H "Authorization: Bearer $TWITTER_BEARER_TOKEN" -d \
        '{
          "add": [
          { 
           "value": "@bts OR #bts",
           "tag": ""
          },
          {
           "value": "@kpop OR #kpop OR @k_pop OR #k_pop",
           "tag": ""
          }
         ] 
        }'

- Manage cURL command

      curl 'https://api.twitter.com/2/tweets/search/stream?tweet.fields=lang,geo,public_metrics,created_at \ 
      &expansions=geo.place_id&place.fields=full_name' \
      -H "Authorization: Bearer $TWITTER_BEARER_TOKEN"

## Getting Started

### first approach:
- run

      spark-submit mindy.jar \[option\] \[input file_name\] \[output dir_name\]

### secon approach:
- make a copy on your local system:
            
      git clone https://github.com/Jeffy892/project-2
    
- go to the analysis folder:

      cd MindyTwitterAnalysis
    
- install scala metals and import build
- compile

      sbt assembly
      
- run

      spark-submit target/scala-2.11/tweetsapp-assembly-0.1.0-SNAPSHOT.jar \[option\] \[input file_name\] \[output dir_name\]
      
- three options:

  * `tweetSparkSql`
  * `tweetMoreSparkSql`
  * `json2csv`

- nine `input` [file name] options for `tweetSparkSql` and `tweetMoreSparkSql`:

  * twitterFilteredStream_210212_geoOnly.json
  * twitterFilteredStream_210213_geoOnly.json
  * BTS_geoOnly_210214_afternoon_CDT.json
  * BTS_geoOnly_210215_morning_CDT.json
  * BTS_geoOnly_210215_1pm-2pm_CDT.json
  * BTS_geoOnly_210215_7pm-8pm_CDT.json
  * BTSkpop_geoOnly_210215_9pm-12am_CDT.json
  * BTS_geoOnly_210216_5am-6am_CDT.json

- nine `input` [file name] options for `json2csv`:

  * ana2_tweetFiltered_210212_allday_geoOnly.json 
  * ana2_tweetFiltered_210212_allday_geoOnly.json
  * ana2_tweetFiltered_210212_allday_geoOnly.json
  * ana2_tweetFiltered_210212_allday_geoOnly.json
  * ana2_tweetFiltered_210212_allday_geoOnly.json
  * ana2_tweetFiltered_210212_allday_geoOnly.json
  * ana2_tweetFiltered_210212_allday_geoOnly.json
  * ana2_tweetFiltered_210212_allday_geoOnly.json 

- nine `output` [directory name] options for `tweetSparkSql`:

  * ana_tweetFiltered_210212_allday_geoOnly_json
  * ana_tweetFiltered_210213_morning_geoOnly_json
  * ana_tweetFiltered_210213_afternoon_geoOnly_CDT_json
  * ana_tweetFiltered_210214_afternoon_geoOnly_CDT_json
  * ana_tweetFiltered_210215_morning_geoOnly_CDT_json
  * ana_tweetFiltered_210215_afternoon_geoOnly_CDT_json
  * ana_tweetFiltered_210215_evening_geoOnly_CDT_json
  * ana_tweetFiltered_210215_night_geoOnly_CDT_json
  * ana_tweetFiltered_210216_morning_geoOnly_CDT_json

 - change `output` file name by making copies residing in `output` folders generated via `tweetSparkSql`:

        cp ./ana_tweetFiltered_210212_allday_geoOnly_json/part* ./ana_tweetFiltered_210212_allday_geoOnly_json/ana_tweetFiltered_210212_allday_geoOnly.json
        cp ./ana_tweetFiltered_210213_morning_geoOnly_json/part* ./ana_tweetFiltered_210213_morning_geoOnly_json/ana_tweetFiltered_210213_morning_geoOnly.json
        cp ./ana_tweetFiltered_210213_afternoon_geoOnly_json/part* ./ana_tweetFiltered_210213_afternoon_geoOnly_json/ana_tweetFiltered_210213_afternoon_geoOnly.json
        cp ./ana_tweetFiltered_210214_afternoon_geoOnly_json/part* ./ana_tweetFiltered_210214_afternoon_geoOnly_json/ana_tweetFiltered_210214_afternoon_geoOnly.json
        cp ./ana_tweetFiltered_210215_morning_geoOnly_json/part* ./ana_tweetFiltered_210215_morning_geoOnly_json/ana_tweetFiltered_210215_morning_geoOnly.json
        cp ./ana_tweetFiltered_210215_afternoon_geoOnly_json/part* ./ana_tweetFiltered_210215_afternoon_geoOnly_json/ana_tweetFiltered_210215_afternoon_geoOnly.json
        cp ./ana_tweetFiltered_210215_evening_geoOnly_json/part* ./ana_tweetFiltered_210215_evening_geoOnly_json/ana_tweetFiltered_210215_evening_geoOnly.json
        cp ./ana_tweetFiltered_210215_night_geoOnly_json/part* ./ana_tweetFiltered_210215_night_geoOnly_json/ana_tweetFiltered_210215_night_geoOnly.json
        cp ./ana_tweetFiltered_210216_morning_geoOnly_json/part* ./ana_tweetFiltered_210216_morning_geoOnly_json/ana_tweetFiltered_210216_morning_geoOnly.json
        
- nine `output` [directory name] options for `tweetMoreSparkSql`: 

  * ana2_tweetFiltered_210212_allday_geoOnly_json
  * ana2_tweetFiltered_210213_morning_geoOnly_json
  * ana2_tweetFiltered_210213_afternoon_geoOnly_CDT_json
  * ana2_tweetFiltered_210214_afternoon_geoOnly_CDT_json
  * ana2_tweetFiltered_210215_morning_geoOnly_CDT_json
  * ana2_tweetFiltered_210215_afternoon_geoOnly_CDT_json
  * ana2_tweetFiltered_210215_evening_geoOnly_CDT_json
  * ana2_tweetFiltered_210215_night_geoOnly_CDT_json
  * ana2_tweetFiltered_210216_morning_geoOnly_CDT_json

- change `output` file names by making copies residing in `output` folders generated via `tweetMoreSparkSql`:

        cp ./ana2_tweetFiltered_210212_allday_geoOnly_json/part* ./ana2_tweetFiltered_210212_allday_geoOnly_json/ana2_tweetFiltered_210212_allday_geoOnly.json
        cp ./ana2_tweetFiltered_210213_morning_geoOnly_json/part* ./ana2_tweetFiltered_210213_morning_geoOnly_json/ana2_tweetFiltered_210213_morning_geoOnly.json
        cp ./ana2_tweetFiltered_210213_afternoon_geoOnly_json/part* ./ana2_tweetFiltered_210213_afternoon_geoOnly_json/ana2_tweetFiltered_210213_afternoon_geoOnly.json
        cp ./ana2_tweetFiltered_210214_afternoon_geoOnly_json/part* ./ana2_tweetFiltered_210214_afternoon_geoOnly_json/ana2_tweetFiltered_210214_afternoon_geoOnly.json
        cp ./ana2_tweetFiltered_210215_morning_geoOnly_json/part* ./ana2_tweetFiltered_210215_morning_geoOnly_json/ana2_tweetFiltered_210215_morning_geoOnly.json
        cp ./ana2_tweetFiltered_210215_afternoon_geoOnly_json/part* ./ana2_tweetFiltered_210215_afternoon_geoOnly_json/ana2_tweetFiltered_210215_afternoon_geoOnly.json
        cp ./ana2_tweetFiltered_210215_evening_geoOnly_json/part* ./ana2_tweetFiltered_210215_evening_geoOnly_json/ana2_tweetFiltered_210215_evening_geoOnly.json
        cp ./ana2_tweetFiltered_210215_night_geoOnly_json/part* ./ana2_tweetFiltered_210215_night_geoOnly_json/ana2_tweetFiltered_210215_night_geoOnly.json
        cp ./ana2_tweetFiltered_210216_morning_geoOnly_json/part* ./ana2_tweetFiltered_210216_morning_geoOnly_json/ana2_tweetFiltered_210216_morning_geoOnly.json

- nine `output` [directory name] options for `json2csv`:

  * ana2_tweetFiltered_210212_allday_geoOnly_csv
  * ana2_tweetFiltered_210213_morning_geoOnly_csv
  * ana2_tweetFiltered_210213_afternoon_geoOnly_CDT_csv
  * ana2_tweetFiltered_210214_afternoon_geoOnly_CDT_csv
  * ana2_tweetFiltered_210215_morning_geoOnly_CDT_csv
  * ana2_tweetFiltered_210215_afternoon_geoOnly_CDT_csv
  * ana2_tweetFiltered_210215_evening_geoOnly_CDT_csv
  * ana2_tweetFiltered_210215_night_geoOnly_CDT_csv
  * ana2_tweetFiltered_210216_morning_geoOnly_CDT_csv

- change `output` file names by making copies residing in `output` folders generated via `json2csv`:

        cp ./ana2_tweetFiltered_210212_allday_geoOnly_csv/part* ./ana2_tweetFiltered_210212_allday_geoOnly_csv/ana2_tweetFiltered_210212_allday_geoOnly.csv
        cp ./ana2_tweetFiltered_210213_morning_geoOnly_csv/part* ./ana2_tweetFiltered_210213_morning_geoOnly_csv/ana2_tweetFiltered_210213_morning_geoOnly.csv
        cp ./ana2_tweetFiltered_210213_afternoon_geoOnly_csv/part* ./ana2_tweetFiltered_210213_afternoon_geoOnly_csv/ana2_tweetFiltered_210213_afternoon_geoOnly.csv
        cp ./ana2_tweetFiltered_210214_afternoon_geoOnly_csv/part* ./ana2_tweetFiltered_210214_afternoon_geoOnly_csv/ana2_tweetFiltered_210214_afternoon_geoOnly.csv
        cp ./ana2_tweetFiltered_210215_morning_geoOnly_csv/part* ./ana2_tweetFiltered_210215_morning_geoOnly_csv/ana2_tweetFiltered_210215_morning_geoOnly.csv
        cp ./ana2_tweetFiltered_210215_afternoon_geoOnly_csv/part* ./ana2_tweetFiltered_210215_afternoon_geoOnly_csv/ana2_tweetFiltered_210215_afternoon_geoOnly.csv
        cp ./ana2_tweetFiltered_210215_evening_geoOnly_csv/part* ./ana2_tweetFiltered_210215_evening_geoOnly_csv/ana2_tweetFiltered_210215_evening_geoOnly.csv
        cp ./ana2_tweetFiltered_210215_night_geoOnly_csv/part* ./ana2_tweetFiltered_210215_night_geoOnly_csv/ana2_tweetFiltered_210215_night_geoOnly.csv
        cp ./ana2_tweetFiltered_210216_morning_geoOnly_json/part* ./ana2_tweetFiltered_210216_morning_geoOnly_csv/ana2_tweetFiltered_210216_morning_geoOnly.csv

before run `tweetSparkSql`              | after run `tweetSparkSql`       
:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/main/MindyTwitterAnalysis/pic/2021-02-20.png)  |  ![](https://github.com/Jeffy892/project-2/blob/main/MindyTwitterAnalysis/pic/2021-02-20%20(1).png) 

## Twitter Data Analysis: List of Features 

* Rank countries where most twitter users are located. 

02/12/2021 all day              | 02/13/2021 morning             |  02/13/2021 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0212_allday_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_morning_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_afternoon_fig1.png)

02/14/2021 afternoon              | 02/15/2021 morning             |  02/15/2021 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0214_afternoon_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_morning_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_afternoon_fig1.png)

02/15/2021 evening              | 02/15/2021 night             |  02/16/2021 morning
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night-2_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0216_morning_fig1.png)

* Count different spoken languages which twitter users in each country tweet in their texts. 

02/12/2021 all day              | 02/13/2021 morning             |  02/13/2021 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0212_allday_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_morning_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_afternoon_fig2.png)

02/14/2021 afternoon              | 02/15/2021 morning             |  02/15/2021 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0214_afternoon_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_morning_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_afternoon_fig2.png)

02/15/2021 evening              | 02/15/2021 night             |  02/16/2021 morning
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night-2_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0216_morning_fig2.png)

* Identify languages to which each given country, where twitter users post tweets, corresponds. 
  
02/12/2021 all day              | 02/13/2021 morning             |  02/13/2021 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0212_allday_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_morning_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_afternoon_fig3.png)

02/14/2021 afternoon              | 02/15/2021 morning             |  02/15/2021 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0214_afternoon_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_morning_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_afternoon_fig3.png)

02/15/2021 evening              | 02/15/2021 night             |  02/16 morning
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night-2_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0216_morning_fig3.png)

## TODOs for future development

* Word Counting (optional for project 2, will do in project 3)
* Twitter Sentimental Analyses & Recommendation Predictive Models using Machine Learning Tree Models & NLP/RNN, e.g. LSTMs/GRUs (optional for project 2, will do in project 3)
