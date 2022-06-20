package josh.bot.core

import scala.util.Random

object PicCommentary {
  def photoResponse: String = {
    val nRNG = Random.nextInt(5)
    nRNG match {
      case response if response == 0 => "Intrigante"
      case response if response == 1 => "Me recuerda a cierto meme, pero no caigo en cual"
      case response if response == 2 => "Neat"
      case response if response == 3 => "Pues no esta mal, not gonna lie"
      case response if response == 4 => "¿Y me estás enseñando esto por...?"
    }
  }
}