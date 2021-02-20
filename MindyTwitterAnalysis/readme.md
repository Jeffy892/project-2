# Project-2 Group 3 Twitter Big Data Analysis

## Twitter data analysis proposal
We would like to explore questions about a popular Korean pop (“Kpop”) artist, BTS from Twitter, while also looking at the other Kpop groups and some information on other pop in America

## Specific Questions to Answer

- **How is #BTS distributed globally? (Mindy Jen)**

## Resposibilities

-  Learn/Understand Twitter API
-  Learn/Understand Spark/Scala (and other useful technologies and frameworks related to our project)
-  Create queries related to our questions
-  Execute queries and analyze
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
- Git + GitHub

## Twitter Data Query

## Twitter Data Analysis: List of Features 

* Rank countries where most twitter users are located. 

02/12 all day              | 02/13 morning             |  02/13 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0212_allday_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_morning_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_afternoon_fig1.png)

02/14 afternoon              | 02/15 morning             |  02/15 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0214_afternoon_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_morning_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_afternoon_fig1.png)

02/15 evening              | 02/15 night             |  02/16 morning
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night-2_fig1.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0216_morning_fig1.png)

* Count different spoken languages which twitter users in each country tweet in their texts. 

02/12 all day              | 02/13 morning             |  02/13 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0212_allday_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_morning_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_afternoon_fig2.png)

02/14 afternoon              | 02/15 morning             |  02/15 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0214_afternoon_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_morning_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_afternoon_fig2.png)

02/15 evening              | 02/15 night             |  02/16 morning
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night-2_fig2.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0216_morning_fig2.png)

* Identify languages to which each given country, where twitter users post tweets, corresponds. 
  
02/12 all day              | 02/13 morning             |  02/13 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0212_allday_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_morning_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0213_afternoon_fig3.png)

02/14 afternoon              | 02/15 morning             |  02/15 afternoon
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0214_afternoon_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_morning_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_afternoon_fig3.png)

02/15 evening              | 02/15 night             |  02/16 morning
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0215_night-2_fig3.png)  |  ![](https://github.com/Jeffy892/project-2/blob/mindy/pic/0216_morning_fig3.png)

## TODOs for future development

* Word Counting (optional for project 2, will do in project 3)
* Twitter Sentimental Analyses & Recommendation Predictive Models using Machine Learning Tree Models & NLP/RNN (optional for project 2, will do in project 3)
