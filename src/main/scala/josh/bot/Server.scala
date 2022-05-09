package josh.bot

import cats.effect._
import fs2.Stream
import josh.bot.telegram.TelegramClient
import josh.bot.config.{Config, FlywayImplementation}
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.ember.client.EmberClientBuilder
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.concurrent.duration.DurationInt

class Server(implicit T: Temporal[IO]) {
  val routes = new HttpRoutes

  def serve: Stream[IO, ExitCode] = for {
    config <- Stream.eval(IO.delay(ConfigSource.default.loadOrThrow[Config]))
    _ <- Stream.eval(FlywayImplementation.migrate[IO](config.jdbc))
    client <- Stream.resource(EmberClientBuilder.default[IO].build)
    telegramClient = new TelegramClient (client, config)
    _ <- BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(routes.helloWorldService.orNotFound)
      .serve
      .concurrently(
        Stream.sleep(1.second) >> Stream.eval(
          telegramClient.serverContact >> telegramClient.messageTest(32478755, TelegramBotMessage.randomMessage)

        )
      )
  } yield ExitCode.Success

}
