package josh.bot.db

import doobie.implicits._
import doobie.postgres.implicits._
import java.util.UUID
import scala.util.Random

import java.util.UUID

object StartQuery {
  def newUser(userUUID: UUID, registerId: Long, registerUser: Option[String]): doobie.Update0 = {
    val newUUID = UUID.randomUUID()
    sql"insert into users(users_id, telegram_id, name) values ($userUUID, $registerId, $registerUser) ON CONFLICT DO NOTHING".update
    //Note: Look for a more efficient way to add the initial preferences list
    //sql"insert into preferences(preferences_id, name, user_name, banned, favorite) values ($newUUID, 897f3c13-3f23-4cbe-b4c7-551767a58e92, $registerId, false, false) ON CONFLICT DO NOTHING".update
    //sql"insert into preferences(preferences_id, name, user_name, banned, favorite) values ($newUUID, 33bcc138-c33f-4faf-9a72-745288e8d07a, $registerId, false, false) ON CONFLICT DO NOTHING".update
    //sql"insert into preferences(preferences_id, name, user_name, banned, favorite) values ($newUUID, 2dadcb13-1859-4fcf-95a9-0b2cf105f51b, $registerId, false, false) ON CONFLICT DO NOTHING".update
    //sql"insert into preferences(preferences_id, name, user_name, banned, favorite) values ($newUUID, 699122f9-28ca-4c85-9ea8-bf4944dc68a6, $registerId, false, false) ON CONFLICT DO NOTHING".update
  }
}
