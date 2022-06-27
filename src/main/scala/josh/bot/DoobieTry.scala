package josh.bot

import cats.effect._
import doobie._
import doobie.implicits._
import josh.bot.config.{Config, JdbcDatabaseConfig}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

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

