import sbt.Keys.version

val projectSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalikejdbc" %% "scalikejdbc" % "3.5.0",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.postgresql" % "postgresql" % "42.2.16",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
  ),
  organization := "com.gigahex.example",

  version := "0.1",
  scalaVersion := "2.12.6"
)

val `hello-scala` = (project in file("."))
  .settings(projectSettings)
  .settings (
    name := "hello-scala",
  )

val web = (project in file("web"))
  .settings(projectSettings)
  .settings(
    name := "hello-web",
    libraryDependencies ++= Seq(
      guice,
      caffeine,
      ws,
      filters
    )
  )
  .enablePlugins(PlayScala)
