import config.Config
import pureconfig._
import pureconfig.generic.auto._

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world")
    ConfigSource.default.loadOrThrow[Config]
  }
}
