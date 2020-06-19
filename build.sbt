import sbtassembly.AssemblyPlugin.autoImport.PathList

lazy val commonSettings = Seq(
  organization := "com.brain",
  name := "yarn-client",
  version := "0.1",
  scalaVersion := "2.11.12",
)

lazy val app = (project in file(".")).
  settings(commonSettings: _*)
  .settings(
    name := "com.brain.yarn-spark-client"
  )


libraryDependencies ++= Seq("org.apache.hadoop" % "hadoop-client" % "2.7.5",
  "org.apache.spark" %% "spark-sql" % "2.4.6" % "provided")



assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}