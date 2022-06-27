package josh.bot.db

import doobie.implicits._
import doobie.postgres.implicits._

import java.util.UUID

object StartQuery {
  def newUser(userUUID: UUID, registerId: Long, registerUser: Option[String]): doobie.Update0 =
    sql"insert into users(users_id, telegram_id, name) values ($userUUID, $registerId, $registerUser) ON CONFLICT DO NOTHING".update
}
