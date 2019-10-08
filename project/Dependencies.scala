import sbt._

object Dependencies {

  val akkaVersion     = "2.5.23"
  val akkaHttpVersion = "10.1.8"
  val logbackVersion  = "1.2.3"

  lazy val core = Seq(
    //SCALA STAFF
    "org.scala-lang.modules"     %% "scala-async"   % "0.10.0" withSources () withJavadoc (),
    "org.scala-lang.modules"     %% "scala-xml"     % "1.2.0" withSources () withJavadoc (),
    "org.scala-lang"             % "scala-reflect"  % "2.13.0" withSources () withJavadoc (),
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2" withSources () withJavadoc (),
    //COMMON LIBS
    "org.typelevel" %% "cats-core"  % "2.0.0-M4" withSources () withJavadoc (),
    "com.beachape"  %% "enumeratum" % "1.5.13" withSources () withJavadoc (),
    "dev.zio" %% "zio" % "1.0.0-RC14" withSources () withJavadoc (),
    //LOGGING
    "net.logstash.logback" % "logstash-logback-encoder" % "6.1" withSources () withJavadoc (),
    "ch.qos.logback"       % "logback-core"             % logbackVersion withSources () withJavadoc (),
    "ch.qos.logback"       % "logback-classic"          % logbackVersion withSources () withJavadoc (),
    "ch.qos.logback"       % "logback-access"           % logbackVersion withSources () withJavadoc ()
    //"" %% "" % "" withSources () withJavadoc (),
  ) ++ commonTesting

  private lazy val commonTesting = Seq(
    "org.scalamock" %% "scalamock" % "4.3.0" % "it, test" withSources () withJavadoc (),
    "org.scalatest" %% "scalatest" % "3.0.8" % "it, test" withSources () withJavadoc ()
  )

  lazy val web = Seq(
    "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion withSources () withJavadoc (),
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion withSources () withJavadoc (),
    "com.typesafe.akka" %% "akka-stream"          % akkaVersion withSources () withJavadoc (),
    "com.typesafe.akka" %% "akka-slf4j"           % akkaVersion withSources () withJavadoc (),
  ) ++ webTesting

  private lazy val webTesting = Seq(
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test withSources () withJavadoc (),
    "com.typesafe.akka" %% "akka-testkit"      % akkaVersion     % Test withSources () withJavadoc ()
  )

  lazy val database = Seq(
    "com.typesafe.slick" %% "slick"     % "3.3.2" exclude ("com.typesafe", "config") withSources () withJavadoc (),
    "org.postgresql"     % "postgresql" % "42.2.5" withSources () withJavadoc ()
  ) ++ databaseTesting

  private lazy val databaseTesting = Seq(
    "org.testcontainers" % "postgresql" % "1.10.6" % "it,test" exclude ("org.slf4j", "slf4j-api") withSources () withJavadoc ()
  )

  private lazy val prometheusVersion = "0.7.0"
  lazy val monitoring = Seq(
    //The client
    "io.prometheus" % "simpleclient" % prometheusVersion withSources () withJavadoc (),
    //Hotspot JVM metrics
    "io.prometheus" % "simpleclient_hotspot" % prometheusVersion withSources () withJavadoc (),
    //Exposition HTTPServer
    "io.prometheus" % "simpleclient_httpserver" % prometheusVersion withSources () withJavadoc (),
    // Pushgateway exposition
    "io.prometheus" % "simpleclient_pushgateway" % prometheusVersion withSources () withJavadoc ()
  )

}
