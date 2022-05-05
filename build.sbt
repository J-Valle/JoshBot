name := "JoshBot"

version := "0.1"

scalaVersion := "2.13.8"

val circeVersion = "0.14.1"
val http4sVersion = "0.23.11"

libraryDependencies ++= Seq(
  "com.github.pureconfig" %% "pureconfig" % "0.17.1",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "org.tpolecat" %% "doobie-postgres" % "1.0.0-RC1",
  "org.flywaydb" % "flyway-core" % "8.5.2",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-generic-extras" % circeVersion,
  "org.tpolecat" %% "doobie-postgres"  % "1.0.0-RC1",
  "org.tpolecat" %% "doobie-core" % "1.0.0-RC1",
  "org.tpolecat" %% "doobie-munit"   % "1.0.0-RC1" % Test
)
