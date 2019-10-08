package ru.shaldnikita.users.port.adapter.dao

import ru.shaldnikita.users.port.adapter.dao.contact.ContactsTable
import ru.shaldnikita.users.port.adapter.dao.user.UsersTable
import ru.shaldnikita.users.port.adapter.dao.contact.ContactsTable
import ru.shaldnikita.users.port.adapter.dao.user.UsersTable
import ru.shaldnikita.users.port.adapter.dao.contact.ContactsTable
import ru.shaldnikita.users.port.adapter.dao.user.UsersTable
import ru.shaldnikita.users.port.adapter.dao.contact.ContactsTable
import ru.shaldnikita.users.port.adapter.dao.user.UsersTable
import slick.jdbc.PostgresProfile.api.{schemaActionExtensionMethods, tableQueryToTableQueryExtensionMethods}
import slick.lifted.TableQuery

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
object Tables {
  val users    = TableQuery[UsersTable]
  val contacts = TableQuery[ContactsTable]

  def tables = Seq(users, contacts)

  def setup = tables.map(_.schema).reduce((a, b) => a ++ b).create
}
