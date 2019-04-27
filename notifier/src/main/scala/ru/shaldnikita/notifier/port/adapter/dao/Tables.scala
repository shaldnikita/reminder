package ru.shaldnikita.notifier.port.adapter.dao

import ru.shaldnikita.notifier.port.adapter.dao.entities.UserTable
import ru.shaldnikita.notifier.port.adapter.dao.entities.UserTable
import slick.lifted.TableQuery

object Tables {
  val users = TableQuery[UserTable]

  def tables = Seq(users)
}
