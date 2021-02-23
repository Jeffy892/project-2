# project-2
## Project Description
Big Data Analysis with Twitter Data

## Technologies Used

SparkSQL
Apache Spark
YARN
Scala 2.12.10
Twitter API
Jupyter Notebook
Python

## Responsibilities

Answer the questions:

1. What are some of the other hashtags that are being used with #BTS?

2. What are the average amount of likes that a common BTS post receives?

## Features

Analyze the data given in the form of JSON files from Twitter Developer.

TO-DO: Change the Jupyter Notebook code to read values from a file, then update the Scala code to output values to a file.

## Getting Started

To run the code, you will need Spark 2.4.7, Hadoop 2.7.7, and SBT to build the jar files.

If you are using the directories from this github, you should have everything else you need to run the code. For reference, here is some information on the input:

The Input for the jar files is the tweetstream directories that are stored inside their respective directories in either hashtagassociation(local) or averagelikes. For the hashtagassociation, the jar file uses streaming, so the only thing needed is a tweetstream directory and one JSON file inside with the format for the tweets so the file can infer the schema. The hashtagassociationlocal is similar to the non-local version, except it actually reads the json file and performs ananlysis with it instead of just infering the schema with it. The average likes takes a file that is collected using:

curl -L -X GET 'https://api.twitter.com/2/users/1409798257/tweets?tweet.fields=lang,geo,public_metrics,created_at' -H "Authorization: Bearer $TWITTER_BEARER_TOKEN"

Where $TWITTER_BEARER_TOKEN is the environment variable for the key necessary to access Twitter data (This command is intended for a Linux environment, for windows, some of the options will be different).

To create the piecharts, you will need to have Anaconda installed and the latest version of Python.

## Usage

The first part of the analysis takes the #BTS and checks for hashtags before #BTS or after #BTS and sees how many times they appear. The analysis only checks for # with english characters and numbers. When finished with a batch, it will print the hashtags that it found along with a count next to them indicating the amount of times it was found.

The second part of the analysis takes a few tweets posted by BTS and checks the average amount of likes they receive and the maximum and minimum amount of likes that they have. Because the schema is different when collecting tweets by a user, trying to use a json file of other tweets would throw an error (more on this below in input).

The pie charts for the first part are hardcoded at the moment, so to create them, you will have to edit the values in the code and then run it.

## License
