package josh.bot
import scala.util.Random

object TelegramBotMessage {
  def randomMessage: String ={

    val nRNG = Random.nextInt(3)

    if (nRNG == 0) {
      "Veo cero carajos"
    }
    else if (nRNG == 1) {
      "Una ostia te voy a dar"
    }
    else if (nRNG == 2) {
      "Dos huevos tengo yo pero Winnie no"
    }
    else {
      "THREEEEE BOOLETS LEEEEEEFT"
    }
  }
}
