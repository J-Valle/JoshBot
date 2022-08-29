package josh.bot.db

import cats.effect.IO
import doobie._
import doobie.implicits._

import java.util.UUID
import scala.util.Random

class PersistenceService(ta: Transactor[IO]) {

  def getFact(telegramUserId : Long): IO[String] = {
    for {
      dbValue <- CurioQueries.dbCount.unique
      nRNG = Random.nextLong(dbValue)
      userId <- getUserId(telegramUserId).unique
      fact <- CurioQueries.chosenFact(nRNG,userId).unique
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

  def changeBans(banned: Boolean, genre : String, users : Long): IO[Int] = {
    preferenceQuery.bannedQuery(banned, genre, users).run.transact(ta)
  }
  def changeFave(fave : Boolean,genre : String, users : Long): IO[Int] = {
    preferenceQuery.favoriteQuery(fave, genre, users).run.transact(ta)
  }
 def preferenceCheck(users: Long): IO[List[(Boolean, Boolean)]] = {
   preferenceQuery.preferenceCheckQuery(users).to[List].transact(ta)
 }
}
