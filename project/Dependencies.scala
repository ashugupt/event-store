import sbt._

object versions {
  val macwireV = "2.2.5"

  val slf4jV = "1.7.21"
  val scalaLoggingV = "3.5.0"
  val logbackV = "1.1.7"

  val akkaV = "2.4.12"
  val akkaHttpV = "3.0.0-RC1"

  val shapelessV = "2.3.2"
  val circeV = "0.6.0-RC1"

  val akkaStreamKafkaV = "0.13"
  val akkaHttpCirceV = "1.10.1"

  val catsV = "0.8.0"
  val fs2V = "0.9.2"
  val fs2CatsV = "0.1.0"

  val http4sV = "0.14.11"

  val asyncHttpClientV = "2.1.0-alpha1"

  val elasticsearchV = "5.0.0"
  val elastic4sV = "3.0.0.ALPHA1"

  val quillV = "1.0.0"

  val kantanCsvV = "0.1.15"

  val nettyV = "4.1.6.Final"

  val kafkaV = "0.10.1.0"
  val confluentV = "3.0.1"

  val scalatestV = "3.0.0"
  val scalacheckV = "1.13.4"
  val mockitoV = "2.2.10"
}

object Dependencies {

  import versions._

  val macwireCore = "com.softwaremill.macwire" %% "macros" % macwireV % "provided"
  val macwireProxy = "com.softwaremill.macwire" %% "proxy" % macwireV
  val macwireUtil = "com.softwaremill.macwire" %% "util" % macwireV

  val slf4j = "org.slf4j" % "slf4j-api" % slf4jV
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingV
  val logback = "ch.qos.logback" % "logback-classic" % logbackV

  val shapeless = "com.chuusai" %% "shapeless" % shapelessV

  val cats = "org.typelevel" %% "cats" % catsV

  val fs2Core = "co.fs2" %% "fs2-core" % fs2V
  val fs2IO = "co.fs2" %% "fs2-io" % fs2V
  val fs2Cats = "co.fs2" %% "fs2-cats" % fs2CatsV

  val circeCore = "io.circe" %% "circe-core" % circeV
  val circeGeneric = "io.circe" %% "circe-generic" % circeV
  val circeParser = "io.circe" %% "circe-parser" % circeV
  val circeJawn = "io.circe" %% "circe-jawn" % circeV
  val circeOptics = "io.circe" %% "circe-optics" % circeV
  val circeLiteral = "io.circe" %% "circe-literal" % circeV
  val circeJava8 = "io.circe" %% "circe-java8" % circeV

  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaV
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaV
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpV
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaV
  val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % akkaHttpCirceV
  val akkaStreamKafka = "com.typesafe.akka" %% "akka-stream-kafka" % akkaStreamKafkaV

  val http4sDsl = "org.http4s" %% "http4s-dsl" % http4sV
  val http4sServer = "org.http4s" %% "http4s-blaze-server" % http4sV
  val http4sClient = "org.http4s" %% "http4s-blaze-client" % http4sV
  val http4sCirce = "org.http4s" %% "http4s-circe" % http4sV

  val asyncHttpClient = "org.asynchttpclient" % "async-http-client" % asyncHttpClientV

  val elasticsearch = "org.elasticsearch" % "elasticsearch" % elasticsearchV

  val quillCassandra = "io.getquill" %% "quill-cassandra" % quillV

  val kafka = "org.apache.kafka" %% "kafka" % kafkaV exclude("org.slf4j", "slf4j-log4j12") exclude("log4j", "log4j")
  val kafkaAvroSerializer = "io.confluent" % "kafka-avro-serializer" % confluentV exclude("org.slf4j", "slf4j-log4j12")

  val netty = "io.netty" % "netty-all" % nettyV

  val kantanCats = "com.nrinaudo" %% "kantan.csv-cats" % kantanCsvV

  val scalaTest = "org.scalatest" %% "scalatest" % scalatestV % "test"
  val scalaCheck = "org.scalacheck" %% "scalacheck" % scalacheckV % "test"
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaV % "test"
  val akkaMultiNodeTestkit = "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaV % "test"
  val akkaStreamTestkit = "com.typesafe.akka" %% "akka-stream-testkit" % akkaV % "test"
  val akkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV % "test"
  val elastic4sTestkit = "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sV % "test"
  val mockitoCore = "org.mockito" % "mockito-core" % mockitoV % "test"
}
