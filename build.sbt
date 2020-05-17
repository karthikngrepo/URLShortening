name := "URLShortening"

version := "0.1"

scalaVersion := "2.12.4"

organization := "ng.dev"

lazy val akkaHttpVersion = "10.1.12"
lazy val akkaVersion    = "2.6.5"
lazy val AkkaVersion = "2.5.31"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.lightbend.akka" %% "akka-stream-alpakka-mongodb" % "2.0.0",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0",
  "commons-codec" % "commons-codec" % "1.14",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.12",

  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)
