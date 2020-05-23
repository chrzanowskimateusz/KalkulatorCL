name := """kalkulatorcl"""
organization := "io.kalkulatorcl"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
      guice,
      jdbc,
      "com.h2database" % "h2" % "1.4.199",
      "org.awaitility" % "awaitility" % "3.1.6" % Test,
      "org.assertj" % "assertj-core" % "3.12.2" % Test,
      "org.mockito" % "mockito-core" % "3.0.0" % Test,
      // To provide an implementation of JAXB-API, which is required by Ebean.
      "javax.xml.bind" % "jaxb-api" % "2.3.1",
      "javax.activation" % "activation" % "1.1.1",
      "org.glassfish.jaxb" % "jaxb-runtime" % "2.3.2",
      "org.jsoup" % "jsoup" % "1.11.2",
      "net.sourceforge.htmlunit" % "htmlunit" % "2.13",
    )