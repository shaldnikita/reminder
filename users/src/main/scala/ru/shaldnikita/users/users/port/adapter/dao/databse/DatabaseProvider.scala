package ru.shaldnikita.users.users.port.adapter.dao.databse

import slick.basic.BasicBackend
import zio.UIO

trait DatabaseProvider {
  def databaseProvider: DatabaseProvider.Service
}

object DatabaseProvider {
  trait Service {
    def db: UIO[BasicBackend#DatabaseDef]
  }
}
