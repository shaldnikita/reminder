package port.adapter.dao

import port.adapter.dao.contact.ContactsTable
import port.adapter.dao.user.UsersTable
import slick.lifted.TableQuery

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
object Tables {
    val users = TableQuery[UsersTable]
    val contacts = TableQuery[ContactsTable]

    def tables = Seq(users, contacts)
}
