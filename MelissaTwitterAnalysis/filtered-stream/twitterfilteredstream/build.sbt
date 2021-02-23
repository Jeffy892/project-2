import Dependencies._

ThisBuild / scalaVersion := "2.11.12"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "melissa-language-static-analysis",
    libraryDependencies += scalaTest % Test,        
    libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.7" % "provided",
    libraryDependencies ++= Seq(
      "org.twitter4j" % "twitter4j-core" % "4.0.7",
      "org.twitter4j" % "twitter4j-stream" % "4.0.7"
      )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
