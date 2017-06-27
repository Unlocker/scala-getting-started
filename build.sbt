import sbt.Keys._

name := """play-getting-started"""

version := "1.0-SNAPSHOT"

lazy val GatlingTest = config("gatling") extend Test

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  jdbc,
  ws,
  guice,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.14",
  "io.dropwizard.metrics" % "metrics-core" % "3.2.1",

  "net.logstash.logback" % "logstash-logback-encoder" % "4.9",
  "org.joda" % "joda-convert" % "1.8",
  "com.netaporter" %% "scala-uri" % "0.4.16",
  "net.codingwell" %% "scala-guice" % "4.1.0",

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0-M3" % Test,
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.5" % Test,
  "io.gatling" % "gatling-test-framework" % "2.2.5" % Test
)

// The Play project itself
lazy val root = (project in file("."))
  .enablePlugins(Common, PlayScala, GatlingPlugin)
  .configs(GatlingTest)
  .settings(inConfig(GatlingTest)(Defaults.testSettings): _*)
  .settings(
    name := """play-scala-rest-api-example""",
    scalaSource in GatlingTest := baseDirectory.value / "/gatling/simulation"
  )