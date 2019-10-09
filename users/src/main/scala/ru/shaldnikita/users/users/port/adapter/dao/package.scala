package ru.shaldnikita.users.users.port.adapter

import ru.shaldnikita.users.DBTask
import slick.dbio.{DBIOAction, NoStream}
import slick.jdbc.PostgresProfile.api._
import zio.ZIO
package object dao {

  implicit case class RichDb(val db: Database) extends AnyVal {
    def runZ[R](a: DBIOAction[R, NoStream, Nothing]): DBTask[R] = {
        ZIO.fromFuture(implicit rc => db.run(a))
    }
  }
}
