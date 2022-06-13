package josh.bot.core

import cats.effect.IO
import fs2.Stream
import io.circe.Json
import josh.bot.TelegramBotMessage
import josh.bot.telegram._
import org.http4s.Uri
import org.http4s.implicits.http4sLiteralsSyntax

import scala.concurrent.duration.DurationInt
class MessageProcess(telegramClient: TelegramClient) {

  def process[TelegramClient]: Stream[IO, Unit] =
    //call serverContact
    //use lastUpdateId to discard old messages
    //return the telegramUpdate list with the new latest updateId
    streamProcess[List[TelegramUpdate]](lastUpdateId =>
      telegramClient.serverContact.map{result =>
        //handle empty list
      val newLastUpdateId = result.result.map(_.updateId).max//maxBy(update => update.updateId).updateId
        //result.result.map(_.updateId).max
        //List.empty[TelegramUpdate].map(_.updateId).max
      val filteredUpdates = result.result.filter(_.updateId > lastUpdateId)

        (newLastUpdateId, filteredUpdates)
      }
    )
      .flatMap(result => Stream.emits(result)).evalMap(update => telegramClient.messageTest(update.message.chat.id, TelegramBotMessage.randomMessage))

  def streamProcess[A](getUpdates: Long => IO[(Long, A)]): Stream[IO, A] =
    Stream
      .awakeDelay[IO](2.seconds)
      .evalMapAccumulate(0L) { (startPoint, _) =>
        getUpdates(startPoint)
      }
      .map(_._2)

}