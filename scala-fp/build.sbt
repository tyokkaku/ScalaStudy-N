name := "scala-fp"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions in Global += "-language:experimental.macros"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

libraryDependencies += "org.mockito" % "mockito-core" % "1.10.19" % "test"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
