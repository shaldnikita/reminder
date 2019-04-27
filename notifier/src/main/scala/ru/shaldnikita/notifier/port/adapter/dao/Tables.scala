package ru.shaldnikita.notifier.port.adapter.dao

import ru.shaldnikita.notifier.port.adapter.dao.entities.{NotificationTable, UserTable}
import slick.lifted.TableQuery

object Tables {
  val users = TableQuery[UserTable]
  val notifications = TableQuery[NotificationTable]

  def tables = Seq(users, notifications)
}
