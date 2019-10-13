package ru.shaldnikita.users.users.port.adapter

import ru.shaldnikita.users.users.port.adapter.dao.databse.DatabaseProvider
import slick.dbio.{DBIO, StreamingDBIO}
import zio.ZIO
import zio.stream.ZStream


package object dao {

  implicit class ZIOObjOps(private val obj: ZIO.type) extends AnyVal {
    def fromDBIO[R](dbio: DBIO[R]): ZIO[DatabaseProvider, Throwable, R] =
      for {
        db <- ZIO.accessM[DatabaseProvider](_.databaseProvider.db)
        r  <- ZIO.fromFuture(ec => db.run(dbio))
      } yield r

    def fromStreamingDBIO[T](dbio: StreamingDBIO[_, T])
      : ZIO[DatabaseProvider, Throwable, ZStream[Any, Throwable, T]] =
      for {
        db <- ZIO.accessM[DatabaseProvider](_.databaseProvider.db)
        r  <- ZIO.effect(db.stream(dbio).toStream())
      } yield r
  }
}
