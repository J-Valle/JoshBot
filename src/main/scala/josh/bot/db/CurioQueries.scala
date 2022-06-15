package josh.bot.db

import doobie.implicits.toSqlInterpolator
//crear mismo paquete db en testing, meter los archivos como el, pero con mis queries y nombres de db, copiar libreria de sbt

object CurioQueries {
  def chosenFact(nRNG: Long): doobie.Query0[String] =
    sql"select curiosity from curious_data offset $nRNG limit 1".query[String]
  val dbCount: doobie.Query0[Long] =
    sql"SELECT COUNT (curiosity) FROM curious_data".query[Long]

}
