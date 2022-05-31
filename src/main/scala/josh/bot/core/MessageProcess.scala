package josh.bot.core

import cats.effect.IO
import fs2.Stream
import josh.bot.TelegramBotMessage
import josh.bot.telegram.TelegramClient

import scala.concurrent.duration.DurationInt
class MessageProcess(telegramClient: TelegramClient) {
  def process[TelegramClient]: Stream[IO, Unit] = Stream.eval(
    telegramClient.serverContact >> telegramClient
      .messageTest(32478755, TelegramBotMessage.randomMessage)
  )

}
