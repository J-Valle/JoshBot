import cats.effect._

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
   new Server().serve.compile.lastOrError

}
