val Http4sVersion = "0.18.3"

lazy val root = (project in file("."))
  .settings(
    organization := "com.l0l",
    name := "ioport",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.4",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "0.9",
      "org.specs2" %% "specs2-core" % "4.0.0" % "test",
      "org.specs2" %% "specs2-matcher-extra" % "4.0.0" % "test",
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion
    ),
    scalacOptions := Seq(
      "-unchecked",
      "-feature",
      "-deprecation",
      "-encoding",
      "utf8",
      "-Ywarn-adapted-args",
      "-Ywarn-inaccessible",
      "-Ywarn-unused:imports",
      "-Ywarn-nullary-override",
      "-Ypartial-unification",
      "-language:higherKinds",
      "-language:implicitConversions"
    )
  )
