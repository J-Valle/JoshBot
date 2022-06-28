package josh.bot.db

import doobie.implicits.toSqlInterpolator

object TimeUpdateQuery {
    def timeUpdate (time: Long) : doobie.Update0 =
      sql"update message_time set time_id = $time".update
    val getTime: doobie.Query0[Long] =
      sql"select time_id from message_time".query[Long]
}
