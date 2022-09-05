package josh.bot.db

import josh.bot.db.preferenceQuery
import doobie.munit.analysisspec.IOChecker
import josh.bot.db._
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import scala.util.Random


class preferenceQuerySuite extends PersistenceServiceSuite with IOChecker {
  test("Preferences works as expected"){
    check(preferenceQuery.bannedQuery(banned = false, "Vida", 1125512))
    check(preferenceQuery.favoriteQuery(fave = false, "Historia", 1125512))
    check(preferenceQuery.preferenceCheckQuery(1125512))
  }


}
