case class TelegramMessageFrom(
    id: Long,
    is_bot: Boolean,
    first_name: String,
    last_name: String,
    username: String,
    language_code: String
)
case class TelegramMessageChat(
    id: Long,
    first_name: String,
    last_name: String,
    username: String,
    chat_type: String
)
case class TelegramMessage(
    message_id: Long,
    from: TelegramMessageFrom,
    chat: TelegramMessageChat,
    date: Long,
    text: String
)
case class TelegramUpdate(
    update_id: Long,
    message: TelegramMessage
)
