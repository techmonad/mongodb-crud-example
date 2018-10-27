name := "reactivemongo-driver-example"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++=
  Seq(
    "org.reactivemongo" %% "reactivemongo" % "0.16.0",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.scalatest" %% "scalatest" % "3.0.4" % Test
  )
