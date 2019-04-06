package ru.shaldnikita.notifier.dao

import ru.shaldnikita.notifier.dao.entities.UserTable
import slick.lifted.TableQuery

object Tables {
  val users = TableQuery[UserTable]

  def tables = Seq(users)
}
