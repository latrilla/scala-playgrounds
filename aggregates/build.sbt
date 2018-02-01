name := "aggregates"

version := "0.1"

scalaVersion := "2.12.4"

mainClass := Some("com.latrilla.Main")

fork in run := true
javaOptions in run ++= Seq("-Xms256M", "-Xmx1G", "-XX:MaxPermSize=1024M")
