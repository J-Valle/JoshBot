package josh.bot.db

import doobie.implicits._
import doobie.postgres.implicits._

import java.util.UUID

object preferenceQuery {

  def bannedQuery(banned: Boolean, genre: String, users: Long): doobie.Update0 = {
    val favToFalse =
      if (banned)
        fr", favorite = false"
      else fr""

    (fr"update preferences set banned = $banned" ++ favToFalse ++ fr" where user_name = (select users_id from users where telegram_id = $users) and name = (select genres_id from genres where name = $genre)").update
  }

  def favoriteQuery(fave: Boolean, genre: String, users: Long): doobie.Update0 = {
    val banToFalse =
      if (fave)
        fr", banned = false"
      else fr""

    (fr"update preferences set favorite = $fave" ++ banToFalse ++ fr"where user_name = (select users_id from users where telegram_id = $users) and name = (select genres_id from genres where name = $genre)").update
  }

 def preferenceCheckQuery(users: Long): doobie.Query0[(Boolean, Boolean)] = {
   sql"select banned, favorite from preferences join users on (preferences.user_name = users.users_id) and users_id = (select users_id from users where telegram_id = $users)".query
 }
}
