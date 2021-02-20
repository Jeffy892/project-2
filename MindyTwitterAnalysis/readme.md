# Project-2 Group 3 Twitter Big Data Analysis

## Twitter data analysis proposal
We would like to explore questions about a popular Korean pop (“Kpop”) artist, BTS from Twitter, while also looking at the other Kpop groups and some information on other pop in America

## Specific Questions to Answer

- **How is #BTS distributed globally? (Mindy Jen)**

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

- 

## Getting Started

- make a copy on your local system:
            
      git clone https://github.com/Jeffy892/project-2
    
- go to the analysis folder:

      cd MindyTwitterAnalysis
    
- install scala metals and import build
- compile

      sbt assembly
      
- run

      spark-submit target/scala-2.11/tweetsapp-assembly-0.1.0-SNAPSHOT.jar \[option\] \[file_name\]
      
- three options:

  * tweetSparkSql
  * tweetMoreSparkSql
  * json2csv

- nine files:

  * 


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
