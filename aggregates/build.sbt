name := "aggregates"

version := "0.1"

scalaVersion := "2.12.4"

mainClass := Some("com.latrilla.aggregates.Main")

fork in run := true
javaOptions in run ++= Seq("-Xms256M", "-Xmx3G")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"


// Release
import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  releaseStepCommandAndRemaining("^ test"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion,
  pushChanges
)
