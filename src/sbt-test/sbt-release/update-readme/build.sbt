import sbtrelease.ReleaseStateTransformations._

releaseReadmeFile := Some(baseDirectory.value / "README.md")

releaseProcess := Seq(
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  updateReadme,
  commitReadme,
  tagRelease,
  setNextVersion,
  commitNextVersion
)

TaskKey[Unit]("checkReadme") := {
  val readmeFile = releaseReadmeFile.value.get
  val content = IO.read(readmeFile)

  assert(
    content ==
      """|# A project
       |
       |Add library in your `build.sbt`
       |
       |```scala
       |libraryDependencies += "com.example" %% "lib" % "2.3.4"
       |```
       |""".stripMargin,
    s"Readme wasn't updated correctly\n\n$content"
  )

}
