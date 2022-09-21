package josh.bot.db

import doobie.implicits._
import doobie.postgres.implicits._

import java.util.UUID

object preferenceQuery {
    def insertFr(
        fave: Boolean,
      banned: Boolean,
      genre: String,
      users: UUID
    ) = {
      val preferenceUUID = UUID.randomUUID()
      fr"""insert into preferences(preferences_id, name, user_name, banned, favorite)
           values ($preferenceUUID, $genre, $users, $banned, $fave)
           on conflict (name, user_name) do
           """
    }

  def bannedQuery(
      banned: Boolean,
      genre: String,
      users: UUID
  ): doobie.Update0 = {

    val favToFalse =
      if (banned)
        fr", favorite = false"
      else fr""

    (insertFr(false, true, genre, users) ++ fr"update preferences set banned = $banned" ++
      favToFalse ++
      fr" where user_name = (select users_id from users where telegram_id = $users) and name = (select genres_id from genres where name = $genre) on conflict ").update
  }

  def favoriteQuery(
      fave: Boolean,
      genre: String,
      users: UUID
  ): doobie.Update0 = {
    val banToFalse =
      if (fave)
        fr", banned = false"
      else fr""

    (insertFr(true, false, genre, users) ++ fr"update preferences set favorite = $fave" ++
      banToFalse ++
      fr"where user_name = $users and name = (select genres_id from genres where name = $genre)").update
  }

  def preferenceCheckQuery(
      users: UUID
  ): doobie.Query0[(String, Boolean, Boolean)] = {
    sql"""select genres.name, banned, favorite
         from preferences
            join users on (preferences.user_name = users.users_id)
            join genres on (preferences.name = genres.genres_id)
         where users_id = $users""".query
  }
}
