import cats.effect.IO
import config.Config
import org.http4s.client.Client
import org.http4s.implicits._
//import org.http4s.circe.CirceEntityCodec._

class TelegramClient (client: Client[IO], config: Config) {
  def serverContact: IO[Unit] =
    client
      .expect[String](uri"https://api.telegram.org" / s"bot${config.telegramToken}" / "getUpdates")
      .map(res => println(res))

}
