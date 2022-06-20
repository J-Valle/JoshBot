package josh.bot.core

import cats.effect.IO
import fs2.Stream
import io.circe.Json
import josh.bot.db.PersistenceService
import josh.bot.telegram._
import org.http4s.Uri
import org.http4s.implicits.http4sLiteralsSyntax

import scala.concurrent.duration.DurationInt
class MessageProcess(
    telegramClient: TelegramClient,
    persistenceService: PersistenceService
) {

  def process: Stream[IO, Unit] =
    streamProcess[List[TelegramUpdate]](lastUpdateId =>
      telegramClient.getUpdates.map { result =>
        val newLastUpdateId =
          result.result.map(_.updateId).maxOption.getOrElse(lastUpdateId)
        val filteredUpdates = result.result.filter(_.updateId > lastUpdateId)

        (newLastUpdateId, filteredUpdates)
      }
    )
      .flatMap(result => Stream.emits(result))
      .evalMap(update => telegramFunction(update))

  def streamProcess[A](getUpdates: Long => IO[(Long, A)]): Stream[IO, A] =
    Stream
      .awakeDelay[IO](2.seconds)
      .evalMapAccumulate(0L) { (startPoint, _) =>
        getUpdates(startPoint)
      }
      .map(_._2)

  def telegramFunction(update: TelegramUpdate): IO[Unit] = {
    (update.message.text, update.message.photo, update.message.video) match {
      case (_, _, Some(video)) =>
        telegramClient.sendMessage(
          update.message.chat.id,
          VideoCommentary.videoResponse
        )
      case (_, photo, _) if photo.nonEmpty =>
        telegramClient.sendMessage(
          update.message.chat.id,
          PicCommentary.photoResponse
        )
      case (Some(text), _, _) if text.startsWith("/curiosidad") =>
        for {
          factMessage <- persistenceService.getFact
          newFactMessage <- telegramClient
            .sendMessage(update.message.chat.id, factMessage)
        } yield newFactMessage
      case invalidValue =>
        telegramClient.sendMessage(
          update.message.chat.id,
          FillerMessage.boringResponse
        )
    }

  }

}
