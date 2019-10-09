package ru.shaldnikita.users

import org.slf4j.LoggerFactory
import zio.{DefaultRuntime, RIO}
import zio.blocking.Blocking
import slick.jdbc.PostgresProfile.api.Database
package object users {

  private val log = LoggerFactory.getLogger("ru.shaldnikita.users")

  type BTask[A] = RIO[Blocking, A]
  type DBTask[A] = RIO[Database, A]
  type BDBTask[A] = RIO[Blocking with Database, A]

  val ZIORuntime: zio.Runtime[Blocking] =
    new DefaultRuntime {}.withReportFailure { cause =>
      if (!cause.interrupted) {
        log.error(cause.prettyPrint)
      }
    }
}
