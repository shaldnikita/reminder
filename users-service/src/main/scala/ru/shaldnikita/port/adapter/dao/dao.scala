package ru.shaldnikita.port.adapter.dao

import slick.jdbc.PostgresProfile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
object dao {
  val db = Database.forConfig("postrges1")

  db.run(Tables.setup)

  sys.addShutdownHook(db.close())

}
