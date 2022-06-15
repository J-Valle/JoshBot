package josh.bot.db

import cats.effect.{ExitCode, IO}
import doobie._
import doobie.implicits._

import scala.util.Random

class PersistenceService(ta: Transactor[IO]) {

  def getFact: IO[String] = {
    {
      for {
        dbValue <- Queries.dbCount.unique
        nRNG = Random.nextInt(dbValue)
        fact <- Queries.chosenFact(nRNG).unique
      } yield fact
    }.transact(ta)

  }

}
