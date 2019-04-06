package ru.shaldnikita.notifier.dao

import ru.shaldnikita.notifier.dao.entities.User
import slick.jdbc.H2Profile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.04.2019
  */
package object dao {
  val inMemoryDatabase = Database.forConfig("h2mem1")

  //todo replace with Tables.tables.map(_.schema).map(_.create)
  val setup = DBIO.seq(Tables.users.schema.create)

  //todo remove
  Tables.users += User("shaldnikita2@yandex.ru",
    "Никита",
    "Шалденков",
    "+79175586982")
}
