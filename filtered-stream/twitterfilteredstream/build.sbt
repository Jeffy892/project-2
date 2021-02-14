import Dependencies._

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "twitterfilteredstream",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      "org.twitter4j" % "twitter4j-core" % "4.0.7",
      "org.twitter4j" % "twitter4j-stream" % "4.0.7"
      // https://mvnrepository.com/artifact/com.google.code.gson/gson
    ),
    libraryDependencies += "com.google.code.gson" % "gson" % "1.7.1"
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
