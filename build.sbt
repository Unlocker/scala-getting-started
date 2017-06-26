name := """play-getting-started"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  jdbc,
  ws,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.14"
)

libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ )
