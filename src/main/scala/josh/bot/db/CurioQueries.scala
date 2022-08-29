package josh.bot.db

import doobie.implicits._
import doobie.postgres.implicits._

import java.util.UUID

object CurioQueries {

  private def bannedCuriosity(userId: UUID) =
    fr"""select curious_data_id
         from curious_data join genres on (curious_data.genre = genres.genres_id) join preferences on (genres.genres_id = preferences.name) and preferences.user_name = $userId
         where banned = true"""

  def chosenFact(nRNG: Long, userId : UUID): doobie.Query0[String] = {
    (fr"""select curiosity from curious_data where curious_data_id not in (""" ++ bannedCuriosity(userId) ++ fr") offset $nRNG limit 1")
      .query[String]
  }

  val dbCount: doobie.Query0[Long] =
    sql"SELECT COUNT (curiosity) FROM curious_data".query[Long]

}
