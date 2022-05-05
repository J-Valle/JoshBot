package josh.bot

import pureconfig.generic.auto._
import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._
import doobie.util.ExecutionContexts
import cats.effect.unsafe.implicits.global
import doobie.util.transactor.Transactor.Aux
import fs2.Stream
import josh.bot.config.{Config, JdbcDatabaseConfig}
import pureconfig.ConfigSource

object DoobieTry extends IOApp.Simple {
  def createTransactor(config: JdbcDatabaseConfig): Transactor[IO] =
  Transactor.fromDriverManager[IO](
          config.driver,
          config.url,
          config.user,
    config.password
  )

  val configuration: Config = ConfigSource.default.loadOrThrow[Config]
  val transactor: doobie.Transactor[IO] = createTransactor(configuration.jdbc)
  val program2: doobie.ConnectionIO[Int] = sql"select 42".query[Int].unique

  override def run: IO[Unit] = program2.transact(transactor).map(println(_)).void
}

