package josh.bot

import cats.effect._
import doobie.Transactor
import fs2.Stream
import josh.bot.telegram.TelegramClient
import josh.bot.config.{Config, FlywayImplementation, JdbcDatabaseConfig}
import josh.bot.core.MessageProcess
import josh.bot.db.PersistenceService
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.ember.client.EmberClientBuilder
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.concurrent.duration.DurationInt

class Server(implicit T: Temporal[IO]) {
  val routes = new HttpRoutes
  def createTransactor(config: JdbcDatabaseConfig): Transactor[IO] =
    Transactor.fromDriverManager[IO](
      config.driver,
      config.url,
      config.user,
      config.password

    )

  def serve: Stream[IO, ExitCode] = for {
    config <- Stream.eval(IO.delay(ConfigSource.default.loadOrThrow[Config]))
    _ <- Stream.eval(FlywayImplementation.migrate[IO](config.jdbc))
    client <- Stream.resource(EmberClientBuilder.default[IO].build)
    telegramClient = new TelegramClient (client, config)
    persistenceTransactor = createTransactor(config.jdbc)
    persistenceService = new PersistenceService(persistenceTransactor)
    messageProcess = new MessageProcess (telegramClient, persistenceService)
    _ <- BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(routes.helloWorldService.orNotFound)
      .serve
      .concurrently(
        Stream.sleep(1.second) >> messageProcess.process
      )
  } yield ExitCode.Success

}
