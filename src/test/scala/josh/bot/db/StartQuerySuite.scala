package josh.bot.db

import josh.bot.db.preferenceQuery
import doobie.munit.analysisspec.IOChecker
import josh.bot.db._
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import scala.util.Random

class StartQuerySuite extends PersistenceServiceSuite with IOChecker {
  test("StartUp funciona correctamente"){
    check(StartQuery.newUser(UUID.randomUUID(), 115512312, Some("TestSubject")))

  }
}
