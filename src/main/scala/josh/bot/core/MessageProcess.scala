package josh.bot.core

import cats.effect.IO
import fs2.Stream
import josh.bot.db.PersistenceService
import josh.bot.telegram._

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

  def streamProcess[A](getUpdates: Long => IO[(Long, A)]): Stream[IO, A] = {
    for {
      updateTimer <- Stream.eval(persistenceService.callTimer)

      updatedStream <- Stream
        .awakeDelay[IO](2.seconds)
        .evalMapAccumulate(updateTimer) { (startPoint, _) =>
          getUpdates(startPoint)
        }
      _ <- Stream.eval(persistenceService.updateTimer(updatedStream._1))
    } yield updatedStream._2

  }

  def telegramFunction(update: TelegramUpdate): IO[Unit] = {
    (update.message.text, update.message.photo, update.message.video) match {
      case (_, _, Some(_)) =>
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
          factMessage <- persistenceService.getFact(update.message.from.id)
          newFactMessage <- telegramClient
            .sendMessage(update.message.chat.id, factMessage)
        } yield newFactMessage

      case (Some(text), _, _) if text.startsWith("/start") =>
        for {
          userIdGet <- persistenceService
            .registerUser(update.message.from.id, update.message.from.username)
            .void
          _ <- telegramClient.sendMessage(
            update.message.chat.id,
            "Hey, me llamo JoshBot, un placer"
          )
        } yield userIdGet

     case (Some(text), _, _) if text.startsWith("/favorito") =>
       telegramClient.sendMessage(update.message.chat.id, "Ok, parte que no te he dicho, ponme que género quieres añadir, como por ejemplo /favorito Historia... y escríbemelo bien, que no son ganas de repetirme.")
         val textList = text.split(" ").toList.tail
         val genreList = List("Historia", "Vida", "Videojuegos", "Random")
       textList match {
         case genre :: Nil if genreList.contains(genre) =>
           for {
             newFave <- persistenceService.changeFave(fave = true, genre, update.message.from.id)
           } yield newFave
         case _ => telegramClient.sendMessage(update.message.chat.id, "Escríbeme bien las cosas, tuso.")
       }

      case (Some(text), _, _) if text.startsWith("/banear") =>
        telegramClient.sendMessage(update.message.chat.id, "Ok, parte que no te he dicho, ponme que género quieres añadir, como por ejemplo /banear Historia... y escríbemelo bien, que no hay ganas de repetirme.")
        val textList = text.split(" ").toList.tail
        val genreList = List("Historia", "Vida", "Videojuegos", "Random")
        textList match {
          case genre :: Nil if genreList.contains(genre) =>
          for {
            newBan <- persistenceService.changeBans(banned = true, genre, update.message.from.id)
          } yield newBan
          case _ => telegramClient.sendMessage(update.message.chat.id, "Escríbeme bien las cosas, tuso.")
        }

      case (Some(text), _, _) if text.startsWith("/mostrar_preferencias") =>
        for {
          userIdGet <- persistenceService.preferenceCheck(update.message.chat.id)
          _ <- telegramClient.sendMessage(update.message.chat.id, "Ok, aqui tienes la lista tusín.")
        } yield userIdGet

      case _ =>
        telegramClient.sendMessage(
          update.message.chat.id,
          FillerMessage.boringResponse
        )
    }

  }

}
