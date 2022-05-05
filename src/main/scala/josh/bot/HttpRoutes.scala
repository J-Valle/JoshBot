package josh.bot

import cats.effect._
import org.http4s
import org.http4s._
import org.http4s.dsl.io._

class HttpRoutes {
  val helloWorldService: http4s.HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
    case GET -> Root / "ping" =>
      Ok("pong")
  }


}
