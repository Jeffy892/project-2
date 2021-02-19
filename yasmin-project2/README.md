# Project 2
Analysis of Twitter data streams

## Description
Using Twitter API and big data tools to answer basic data analysis questions

## Overview
* We chose to focus on the BTS hashtag on twitter, adding rules for our API pull requests for filtered stream
* What are the common words associated with the BTS hashtag?
* * How does this vary over time?
* Extract words, filter out words that are less than 3 letters, and group by then count the words in descending order
* Extract words, group by words and time in descending order to see when the most popular words are used

## Demo
![image](https://user-images.githubusercontent.com/54565666/108534895-469d9b80-72a0-11eb-87d8-343aa0842156.png)

## Technologies Used
* Scala
* Apache Spark
* Twitter API
* SBT
* Maven
* Scala Metals

## Getting Started
* git clone https://github.com/Jeffy892/project-2
* cd into yasmin-project2
* install scala metals and import build
* sbt assembly
* spark-submit target/scala-2.11/project2-assembly-0.1.0-SNAPSHOT.jar
* uncomment line 34, comment line 33, and repeat last two steps to see second part of analysis

## Usage
* To see the answer to my portion of the analysis, the table will be printed out to console, just like the demo

## Contributers
* yyazdi13
* renjmindy
* melissawhite176
* Jeffy892
* ClairHoulihan