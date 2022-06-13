package josh.bot.telegram

import cats.effect.IO
import io.circe.Json
import josh.bot.TelegramBotMessage
import josh.bot.config.Config
import org.http4s.Uri
import org.http4s.client.Client
import org.http4s.implicits._
import org.http4s.circe.CirceEntityCodec._

class TelegramClient (client: Client[IO], config: Config) {

  val baseUri: Uri = uri"https://api.telegram.org" / s"bot${config.telegramToken}"
  def serverContact: IO[TelegramResult[List[TelegramUpdate]]] =
    client
      .expect[TelegramResult[List[TelegramUpdate]]]( baseUri / "getUpdates")

  def messageTest(chatId: Long, text: String): IO[Unit] =
    {
      client
        .expect[TelegramResult[Json]]((baseUri / "sendMessage").withQueryParam("chat_id", chatId).withQueryParam("text",text ))
        .map(res =>if(res.ok)() else println(res.result.spaces2))
    }

}
