lazy val root =
  project
    .in(file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      scalaVersion := "2.12.1",
      scalacOptions := Seq("-feature", "-language:_", "-deprecation"),
      libraryDependencies ++= Seq(
        "com.lihaoyi" %%% "scalatags" % "0.6.3",
        "org.akka-js" %%% "akkajsactorstream" % "0.2.5.0-RC2-SNAPSHOT",
        "org.akka-js" %%% "akkajsstreamtestkit" % "0.2.5.0-RC2-SNAPSHOT" % "test"
      ),
      jsDependencies +=
        "org.webjars.bower" % "diff-dom" % "2.0.3" / "diffDOM.js",
      scalaJSUseMainModuleInitializer := true,
      skip in packageJSDependencies := false
    )
