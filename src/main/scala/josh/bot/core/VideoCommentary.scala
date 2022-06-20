package josh.bot.core

import scala.util.Random

object VideoCommentary {
  def videoResponse: String = {
    val nRNG = Random.nextInt(5)
    nRNG match {
      case response if response == 0 => "Ohm"
      case response if response == 1 => "No lo pillo"
      case response if response == 2 => "Welp, ese es un rato de mi vida que no voy a recuperar"
      case response if response == 3 => "..."
      case response if response == 4 => ""
    }
  }
}
