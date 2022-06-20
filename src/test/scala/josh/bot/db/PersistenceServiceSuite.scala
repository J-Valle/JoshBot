package josh.bot.db

import cats.effect.IO
import com.dimafeng.testcontainers.PostgreSQLContainer
import com.dimafeng.testcontainers.munit.TestContainerForAll
import doobie.util.transactor.Transactor
import munit.CatsEffectSuite
import org.flywaydb.core.Flyway
import org.testcontainers.utility.DockerImageName

trait PersistenceServiceSuite extends CatsEffectSuite with TestContainerForAll {

  val driverName: String = "org.postgresql.Driver"
  val dbName: String = "josh_bot"
  val dbUserName: String = "username"
  val dbPassword: String = "password"

  override val containerDef: PostgreSQLContainer.Def = PostgreSQLContainer.Def(
    dockerImageName = DockerImageName.parse("postgres:14.2"),
    databaseName = dbName,
    username = dbUserName,
    password = dbPassword
  )

  lazy val transactor: Transactor[IO] = withContainers(pg =>
    Transactor.fromDriverManager[IO](
      driverName,
      pg.jdbcUrl,
      dbUserName,
      dbPassword
    )
  )

  override def afterContainersStart(containers: Containers): Unit =
    withContainers(pg =>
      IO(
        Flyway
          .configure()
          .dataSource(pg.jdbcUrl, dbUserName, dbPassword)
          .load()
          .migrate()
      ).void.unsafeRunSync()
    )

}