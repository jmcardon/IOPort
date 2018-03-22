
lazy val root = (project in file("."))
  .settings(
    organization := "com.l0l",
    name := "ioport",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.4",
    libraryDependencies ++= Seq(
        "org.typelevel" %% "cats-effect" % "0.10",
        "org.specs2" %% "specs2-core"          % "4.0.0" % "test",
        "org.specs2" %% "specs2-matcher-extra" % "4.0.0" % "test"
    )
  )

