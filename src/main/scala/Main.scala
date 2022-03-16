import cats.effect._
import config.{Config, FlywayImplementation, JdbcDatabaseConfig}
import org.http4s.blaze.server.BlazeServerBuilder
import pureconfig._
import pureconfig.generic.auto._
object Main extends IOApp {

  val routes = new HttpRoutes
  def run(args: List[String]): IO[ExitCode] = {
    for {
      config <- IO.delay(ConfigSource.default.loadOrThrow[Config])
      _ <- FlywayImplementation.migrate[IO](config.exampleJdbc)
      _ <- BlazeServerBuilder[IO]
        .bindHttp(8080, "localhost")
        .withHttpApp(routes.helloWorldService.orNotFound)
        .serve
        .compile
        .drain
    } yield ExitCode.Success

  }

}
