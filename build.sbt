import sbt.Keys.version


val projectSettings = Seq(
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
  ),
  organization := "com.gigahex.example",
  name := "hello-scala",
  version := "0.1",
  scalaVersion := "2.12.6"
)

val `hello-scala` = (project in file("."))
  .settings(projectSettings)
