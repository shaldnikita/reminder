package port.adapter.dao

import slick.jdbc.H2Profile.api._
/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
object dao {
  val db = Database.forConfig("h2mem1")

  val setup = DBIO.seq(
    Tables.users.schema.create,
    Tables.contacts.schema.create,
  )

  db.run(setup)

  sys.addShutdownHook(db.close())

}
