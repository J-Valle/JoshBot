package josh.bot.config

import cats.effect.Sync
import cats.implicits._
import org.flywaydb.core.Flyway

object FlywayImplementation {

  def migrate[F[_]: Sync](config: JdbcDatabaseConfig): F[Unit] =
    Sync[F].delay {
      Flyway.configure
        .dataSource(
          config.url,
          config.user,
          config.password
        )
        .load()
        .migrate()
    }.void
}
