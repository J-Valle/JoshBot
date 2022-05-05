package josh.bot.telegram

import io.circe._
import io.circe.generic.extras.semiauto._

case class TelegramMessageFrom(
    id: Long,
    isBot: Boolean,
    firstName: String,
    lastName: String,
    username: String,
    languageCode: String
)

object TelegramMessageFrom {
  implicit val codec: Codec[TelegramMessageFrom] = deriveConfiguredCodec
}

case class TelegramMessageChat(
    id: Long,
    firstName: String,
    lastName: String,
    username: String,
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

case class TelegramResult(
    ok: Boolean,
    result: List[TelegramUpdate]
)

object TelegramResult {
  implicit val codec: Codec[TelegramResult] = deriveConfiguredCodec
}
