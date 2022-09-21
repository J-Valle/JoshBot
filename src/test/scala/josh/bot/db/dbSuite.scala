package josh.bot.db

import josh.bot.db.CurioQueries
import doobie.munit.analysisspec.IOChecker
import josh.bot.db._

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import scala.util.Random

class dbSuite extends PersistenceServiceSuite with IOChecker {

  test ("User registration works as expected"){
    check(getUserId(194950192))
  }

}
