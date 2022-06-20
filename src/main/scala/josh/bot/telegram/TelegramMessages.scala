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
  implicit val codec: Codec[TelegramMessageFrom] = deriveConfiguredCodec
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

case class TelegramMessage(
    messageId: Long,
    from: TelegramMessageFrom,
    chat: TelegramMessageChat,
    date: Long,
    text: String
)

object TelegramMessage {
  implicit val codec: Codec[TelegramMessage] = deriveConfiguredCodec
}

case class TelegramUpdate(
    updateId: Long,
    message: TelegramMessage
)

object TelegramUpdate {
  implicit val codec: Codec[TelegramUpdate] = deriveConfiguredCodec
}

case class TelegramResult[A](
    ok: Boolean,
    result: A
)

object TelegramResult {
  implicit val jsonCodec: Codec[TelegramResult[Json]] = deriveConfiguredCodec
  implicit val updateCodec: Codec[TelegramResult[List[TelegramUpdate]]] = deriveConfiguredCodec
}
