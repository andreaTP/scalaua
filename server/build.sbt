lazy val root =
  project
    .in(file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      scalaVersion := "2.12.1",
      libraryDependencies ++= Seq(
        "org.akka-js" %%% "akkajsactor" % "0.2.5.0-RC2-SNAPSHOT",
        "org.akka-js" %%% "akkajstestkit" % "0.2.5.0-RC2-SNAPSHOT" % "test"
      ),
      fork in run := true,
      cancelable in Global := true
    )
