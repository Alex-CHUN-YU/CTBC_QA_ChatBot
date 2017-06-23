name := """SpanishCeateBackendServer"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "5.0.8"
)
// https://mvnrepository.com/artifact/net.sf.supercsv/super-csv
libraryDependencies += "net.sf.supercsv" % "super-csv" % "2.4.0"
// https://mvnrepository.com/artifact/tw.cheyingwu/CKIPClient
libraryDependencies += "tw.cheyingwu" % "CKIPClient" % "0.4.3"
// https://mvnrepository.com/artifact/org.json/json
libraryDependencies += "org.json" % "json" % "20160810"
// https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
libraryDependencies += "com.googlecode.json-simple" % "json-simple" % "1.1.1"
PlayKeys.devSettings := Seq("play.server.http.port" -> "9003")
