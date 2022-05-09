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
  def serverContact: IO[Unit] =
    client
      .expect[TelegramResult]( baseUri / "getUpdates")
      .map(res => println(res))

  def messageTest(chatId: Long, text: String): IO[Unit] =
    {

      client
        .expect[Json]((baseUri / "sendMessage").withQueryParam("chat_id", chatId).withQueryParam("text",text ))
        .map(res => println(res.spaces2))
    }

}
