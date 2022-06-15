package josh.bot.db

import doobie.implicits.toSqlInterpolator

object Queries {
  def chosenFact(nRNG: Int): doobie.Query0[String] =
    sql"select curiosity from curious_data offset $nRNG limit 1".query[String]
  val dbCount: doobie.Query0[Int] = sql"select count curiosity from curious_data".query[Int]


}
