package josh.bot.db

import josh.bot.db.CurioQueries
import doobie.munit.analysisspec.IOChecker
import josh.bot.db._

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import scala.util.Random


class CurioQueriesSuite extends PersistenceServiceSuite with IOChecker {
  test("Curioisities works as expected"){
    check(CurioQueries.chosenFact(9L))
    check(CurioQueries.dbCount)
  }



}
