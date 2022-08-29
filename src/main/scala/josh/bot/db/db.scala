package josh.bot

import doobie.implicits._
import doobie.postgres.implicits._

import java.util.UUID

package object db
{
  def getUserId (telegramUserId : Long): doobie.Query0[UUID] =
    sql"select users_id from users where telegram_id = $telegramUserId".query[UUID]
}
