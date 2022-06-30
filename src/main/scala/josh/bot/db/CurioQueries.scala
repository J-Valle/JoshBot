package josh.bot.db

import doobie.implicits.toSqlInterpolator

object CurioQueries {
  def chosenFact(nRNG: Long): doobie.Query0[String] = {
    sql"select curiosity from curious_data offset $nRNG limit 1 except (select curiosity, genres.name from curious_data join genres on (curious_data.genre = genres.genres_id) join preferences on (genres.genres_id = preferences.name) where banned = true) ".query[String]
  }

  val dbCount: doobie.Query0[Long] =
    sql"SELECT COUNT (curiosity) FROM curious_data".query[Long]

}
