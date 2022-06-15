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
    //call serverContact
    //use lastUpdateId to discard old messages
    //return the telegramUpdate list with the new latest updateId
    streamProcess[List[TelegramUpdate]](lastUpdateId =>
      telegramClient.serverContact.map { result =>
        //handle empty list
        val newLastUpdateId =
          result.result.map(_.updateId).maxOption.getOrElse(lastUpdateId)
        val filteredUpdates = result.result.filter(_.updateId > lastUpdateId)

        (newLastUpdateId, filteredUpdates)
      }
    )
      .flatMap(result => Stream.emits(result))
      .evalMap(update => telegramFunction(update)
      )

  def streamProcess[A](getUpdates: Long => IO[(Long, A)]): Stream[IO, A] =
    Stream
      .awakeDelay[IO](2.seconds)
      .evalMapAccumulate(0L) { (startPoint, _) =>
        getUpdates(startPoint)
      }
      .map(_._2)

  def telegramFunction(update: TelegramUpdate): IO[Unit] = {

    update.message.text match{
      case text if text.startsWith("/curiosidad") =>
        for {
          factMessage <- persistenceService.getFact
          newFactMessage <- telegramClient
            .messageTest(update.message.chat.id, factMessage)
        } yield newFactMessage
      case invalidText => telegramClient.messageTest(update.message.chat.id, s"$invalidText no es un comando aceptado." )
    }

  }

}
