name := "permission-tree"

version := "0.4.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.0" % "test"
)

scalacOptions ++= Seq(
  "-language:reflectiveCalls"
)