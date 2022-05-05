package josh.bot

import io.circe.generic.extras.Configuration

package object telegram {
  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames
}
