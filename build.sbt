name := "JoshBot"

version := "0.1"

scalaVersion := "2.13.8"

val http4sVersion = "0.23.10"

libraryDependencies ++= Seq(
  "com.github.pureconfig" %% "pureconfig" % "0.17.1",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.tpolecat" %% "doobie-postgres"  % "1.0.0-RC1",
  "org.flywaydb" % "flyway-core" % "8.5.2"
)