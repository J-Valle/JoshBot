package josh.bot.db

import cats.effect.IO
import doobie._
import doobie.implicits._

import java.util.UUID
import scala.util.Random

class PersistenceService(ta: Transactor[IO]) {

  def getFact: IO[String] = {
    for {
      dbValue <- CurioQueries.dbCount.unique
      nRNG = Random.nextLong(dbValue)
      fact <- CurioQueries.chosenFact(nRNG).unique
    } yield fact
  }.transact(ta)

  def registerUser(startUp: Long, userStart: Option[String]): IO[Int] = {
    StartQuery
      .newUser(userUUID = UUID.randomUUID(), startUp, userStart)
      .run
      .transact(ta)
  }

  def updateTimer(timeValue: Long): IO[Int] = {
    TimeUpdateQuery.timeUpdate(timeValue).run.transact(ta)
  }

  val callTimer: IO[Long] = TimeUpdateQuery.getTime.unique.transact(ta)

}
