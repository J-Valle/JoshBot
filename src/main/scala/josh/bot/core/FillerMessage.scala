package josh.bot.core

import scala.util.Random

object FillerMessage {
   def boringResponse: String = {
    val nRNG = Random.nextInt(5)
    nRNG match {
      case response if response == 0 => "¿Eh? Perdona, estaba a otra cosa."
      case response if response == 1 => "Lo siento tuso, estaba escuchando música."
      case response if response == 2 => "Yep, asi fue como pude resolverlo... me estabas escuchando... ¿No?"
      case response if response == 3 => "...."
      case response if response == 4 => "Hm, yep y tal."
    }
  }
}
