import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._

lazy val root =
  (project in file("."))
    .enablePlugins(JavaAppPackaging, GitVersioning, BuildInfoPlugin)
    .settings(commonSettings: _*)
    .settings(compilationSettings: _*)
    .settings(
      name := "events-service",
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "com.ashugupt.eventstore"
    )

lazy val ingester =
  (project in file("ingester"))
    .enablePlugins(JavaAppPackaging, GitVersioning, BuildInfoPlugin)
    .settings(commonSettings: _*)
    .settings(compilationSettings: _*)
    .settings(libraryDependencies ++= commonLibs ++ streamLibs)
    .settings(
      name := "events-ingester",
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "com.ashugupt.eventstore.ingester"
    )
    .dependsOn(store)

lazy val http =
  (project in file("http"))
    .enablePlugins(JavaAppPackaging, GitVersioning, BuildInfoPlugin)
    .settings(commonSettings: _*)
    .settings(compilationSettings: _*)
    .settings(libraryDependencies ++= commonLibs ++ httpLibs)
    .settings(
      name := "events-http",
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "com.ashugupt.eventstore.http"
    )
    .dependsOn(store, ingester)

lazy val api =
  (project in file("api"))
    .enablePlugins(JavaAppPackaging, GitVersioning, BuildInfoPlugin)
    .settings(commonSettings: _*)
    .settings(compilationSettings: _*)
    .settings(libraryDependencies ++= commonLibs ++ apiLibs)
    .settings(
      name := "events-api",
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "com.ashugupt.eventstore.api"
    )
    .dependsOn(store, ingester)

lazy val store =
  (project in file("store"))
    .enablePlugins(JavaAppPackaging, GitVersioning, BuildInfoPlugin)
    .settings(commonSettings: _*)
    .settings(compilationSettings: _*)
    .settings(libraryDependencies ++= commonLibs ++ storeLibs)
    .settings(
      name := "events-store",
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "com.ashugupt.eventstore.store"
    )
    .aggregate(storeCassandra, storeElasticSearch)

lazy val storeCassandra =
  (project in file("store/store-cassandra"))
    .enablePlugins(JavaAppPackaging, GitVersioning, BuildInfoPlugin)
    .settings(commonSettings: _*)
    .settings(compilationSettings: _*)
    .settings(libraryDependencies ++= commonLibs ++ storeLibs)
    .settings(
      name := "events-store-cassandra",
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "com.ashugupt.eventstore.store.storeCassandra"
    )

lazy val storeElasticSearch =
  (project in file("store/store-elasticsearch"))
    .enablePlugins(JavaAppPackaging, GitVersioning, BuildInfoPlugin)
    .settings(commonSettings: _*)
    .settings(compilationSettings: _*)
    .settings(libraryDependencies ++= commonLibs ++ storeLibs)
    .settings(
      name := "events-store-elasticsearch",
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "com.ashugupt.eventstore.store.storeElasticSearch"
    )


lazy val commonSettings = Vector(
  organization := "com.ashugupt.eventstore",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.8",

  promptTheme := com.scalapenos.sbt.prompt.PromptThemes.ScalapenosTheme,

  ammonite.AmmonitePlugin.ammoniteVersion := "0.7.9",

  resolvers ++= Seq(
    Opts.resolver.mavenLocalFile,
    "Typesafe repository" at "https://repo.typesafe.com/typesafe/maven-releases/",
    "Confluent" at "http://packages.confluent.io/maven"
  )

) ++ gitSettings ++ releaseSettings ++ tutSettings

lazy val gitSettings = Vector(
  git.baseVersion := "0.0.0",

  {
    val VersionRegex = "v([0-9]+.[0-9]+.[0-9]+)-?(.*)?".r

    git.gitTagToVersionNumber := {
      case VersionRegex(v, "") => Some(v)
      case VersionRegex(v, "SNAPSHOT") => Some(s"$v-SNAPSHOT")
      case VersionRegex(v, s) => Some(s"$v-$s-SNAPSHOT")
      case _ => None
    }
  }
)

lazy val releaseSettings = Vector(
  // release-settings
  publishMavenStyle := true,

  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },

  publishArtifact in Test := false,

  pomIncludeRepository := { _ => false },

  pomExtra :=
    <url>https://github.com/ashugupt/event-store</url>
      <licenses>
        <license>
          <name>Apache2</name>
          <url>https://www.apache.org/licenses/LICENSE-2.0</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:ashugupt/event-store.git</url>
        <connection>scm:git:git@github.com:ashugupt/event-store.git</connection>
      </scm>
      <developers>
        <developer>
          <id>ashugupt</id>
          <name>Ashu Gupta</name>
          <url>https://github.com/ashugupt</url>
        </developer>
      </developers>
)

lazy val commonLibs = Vector(
  Dependencies.slf4j,
  Dependencies.scalaLogging,
  Dependencies.logback,

  Dependencies.shapeless,

  Dependencies.cats,
  Dependencies.fs2Core,
  Dependencies.fs2IO,
  Dependencies.fs2Cats,

  Dependencies.circeCore,
  Dependencies.circeGeneric,
  Dependencies.circeJawn,
  Dependencies.circeLiteral,
  Dependencies.circeParser,
  Dependencies.circeOptics,
  Dependencies.circeJava8,

  Dependencies.macwireCore,
  Dependencies.macwireProxy,
  Dependencies.macwireUtil,

  Dependencies.scalaCheck,
  Dependencies.mockitoCore,
  Dependencies.scalaTest
)

lazy val storeLibs = Vector(
  Dependencies.elasticsearch,

  Dependencies.quillCassandra
)

lazy val streamLibs = Vector(
  Dependencies.kantanCats,

  Dependencies.akkaStreamKafka,
  Dependencies.kafka,
  Dependencies.kafkaAvroSerializer
)

lazy val httpLibs = Vector(
  Dependencies.http4sDsl,
  Dependencies.http4sServer,
  Dependencies.http4sClient,
  Dependencies.http4sCirce
)

lazy val apiLibs = Vector(
  Dependencies.akkaSlf4j,
  Dependencies.akkaHttp,
  Dependencies.akkaHttpCirce,

  Dependencies.akkaStreamKafka,

  Dependencies.akkaHttpTestkit,
  Dependencies.akkaStreamTestkit
)

lazy val compilationSettings = Vector(
  // compile settings
  compileOrder := CompileOrder.Mixed,

  javaOptions ++= Vector(
    "-Dammonite.version=0.7.9"
  ),

  // javac settings
  javacOptions ++= Vector(
    "-g:none",
    "-source", "1.8",
    "-target", "1.8",
    "-Xlint"
  ),

  // scalac settings
  scalacOptions ++= Vector(
    "-unchecked",
    "-feature",
    "-deprecation",
    "-language:implicitConversions",
    "-language:higherKinds",
    "-language:existentials",
    "-language:postfixOps",
    "-target:jvm-1.8",
    "-encoding", "UTF-8",
    "-Xfuture",
    //    "-Xfatal-warnings",
    "-Xlint",
    "-Ywarn-dead-code",
    "-Ywarn-adapted-args"
  )
)
