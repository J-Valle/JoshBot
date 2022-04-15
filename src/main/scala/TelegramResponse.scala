import io.circe._
import io.circe.generic.semiauto._

case class TelegramResponse(i: Int, d: Option[Double])
object TelegramResponse {
  implicit val encoder: Encoder[TelegramResponse] = deriveEncoder[TelegramResponse]
  implicit val decoder: Decoder[TelegramResponse] = deriveDecoder[TelegramResponse]
}

