package ru.shaldnikita.port.adapter.dao

import ru.shaldnikita.port.adapter.dao.contact.ContactsTable
import ru.shaldnikita.port.adapter.dao.user.UsersTable
import slick.lifted.TableQuery

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
object Tables {
  val users    = TableQuery[UsersTable]
  val contacts = TableQuery[ContactsTable]

  def tables = Seq(users, contacts)
}