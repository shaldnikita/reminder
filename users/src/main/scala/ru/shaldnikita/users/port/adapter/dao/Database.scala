package ru.shaldnikita.users.port.adapter.dao

import slick.jdbc.PostgresProfile.api

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
object Database {
  val db = api.Database.forConfig("postrges1")

  // create schemas
  db.run(Tables.setup)

  sys.addShutdownHook(db.close())
}
