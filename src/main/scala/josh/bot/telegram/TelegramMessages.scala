package josh.bot.telegram

import io.circe._
import io.circe.generic.extras.semiauto._

case class TelegramMessageFrom(
    id: Long,
    isBot: Boolean,
    firstName: String,
    lastName: Option[String],
    username: Option[String],
    languageCode: String
)
object TelegramMessageFrom {
  implicit val codec: Decoder[TelegramMessageFrom] = deriveConfiguredDecoder
}

case class TelegramMessageChat(
    id: Long,
    firstName: String,
    lastName: Option[String],
    username: Option[String],
    chatType: String
)
object TelegramMessageChat {
  implicit val encoder: Encoder[TelegramMessageChat] =
    Encoder
      .forProduct5(
        "id",
        "first_name",
        "last_name",
        "username",
        "type"
      )(tmc =>
        (tmc.id, tmc.firstName, tmc.lastName, tmc.username, tmc.chatType)
      )
  implicit val decoder: Decoder[TelegramMessageChat] = Decoder.forProduct5(
    "id",
    "first_name",
    "last_name",
    "username",
    "type"
  )(TelegramMessageChat.apply)
}
object TelegramMessageVideoThumb {
  implicit val codec: Decoder[TelegramMessageVideoThumb] = deriveConfiguredDecoder
}
case class TelegramMessageVideoThumb(
  fileId: String,
  fileUniqueId: String,
  fileSize: Long,
  width: Int,
  height: Int
)
object TelegramMessageVideo {
  implicit val codec: Decoder[TelegramMessageVideo] = deriveConfiguredDecoder
}
case class TelegramMessageVideo(
  duration: Int,
  width: Int,
  height: Int,
  mimeType: String,
  thumb: TelegramMessageVideoThumb,
  fileId: String,
  fileUniqueId: String,
  fileSize: Long
)
object TelegramMessageImage {
  implicit val codec: Decoder[TelegramMessageImage] = deriveConfiguredDecoder
}
case class TelegramMessageImage(
  fileId: String,
  fileUniqueId: String,
  fileSize: Long,
  width: Int,
  height: Int
)

case class TelegramMessage(
    messageId: Long,
    from: TelegramMessageFrom,
    chat: TelegramMessageChat,
    date: Long,
    text: Option[String],
    photo: List[TelegramMessageImage],
    video: Option[TelegramMessageVideo]
){
  def isAPicture: Boolean = photo.nonEmpty
}

object TelegramMessage {
  implicit def listDecoder[A: Decoder]: Decoder[List[A]] =
    Decoder.decodeOption(Decoder.decodeList[A]).map(_.getOrElse(Nil))
  implicit val codec: Decoder[TelegramMessage] = deriveConfiguredDecoder
}

case class TelegramUpdate(
    updateId: Long,
    message: TelegramMessage
)

object TelegramUpdate {
  implicit val codec: Decoder[TelegramUpdate] = deriveConfiguredDecoder
}

case class TelegramResult[A](
    ok: Boolean,
    result: A
)

object TelegramResult {
  implicit val jsonCodec: Decoder[TelegramResult[Json]] = deriveConfiguredDecoder
  implicit val updateCodec: Decoder[TelegramResult[List[TelegramUpdate]]] =
    deriveConfiguredDecoder
}
