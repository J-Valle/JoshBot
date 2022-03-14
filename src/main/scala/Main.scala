import cats.effect.IOApp
import config.Config
import pureconfig._
import pureconfig.generic.auto._
import cats.effect._
import org.http4s.blaze.server.BlazeServerBuilder

import scala.concurrent.ExecutionContext.global
object Main extends IOApp {
//  def main(args: Array[String]): Unit = {
//    println("Hello world")
//    ConfigSource.default.loadOrThrow[Config]
//  }
  val routes = new HttpRoutes
  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(routes.helloWorldService.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
