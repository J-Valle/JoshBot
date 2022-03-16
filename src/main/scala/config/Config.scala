package config

final case class JdbcDatabaseConfig(
  driver: String,
  url: String,
  user: String,
  password: String
)

case class Config (
    telegramToken : String,
    exampleJdbc : JdbcDatabaseConfig
)
