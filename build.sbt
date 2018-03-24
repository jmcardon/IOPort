

val http4sVersion = "0.18.3"
val circeVersion = "0.9.2"
val catsEffectVersion = "0.10"
val catsVersion = "1.1.0"
val monixVersion = "3.0.0-M3"

lazy val root = (project in file("."))
  .settings(
    organization := "com.l0l",
    name := "ioport",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.4",
    libraryDependencies ++= List(
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.typelevel" %% "cats-core" % catsVersion,
      "org.specs2" %% "specs2-core" % "4.0.0" % "test",
      "org.specs2" %% "specs2-matcher-extra" % "4.0.0" % "test",
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.http4s" %% "http4s-twirl" % http4sVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "io.circe" %% "circe-literal" % circeVersion,
      "io.monix" %% "monix" % monixVersion
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
