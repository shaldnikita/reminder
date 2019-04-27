package ru.shaldnikita.notifier.port.adapter.dao.repositories

import ru.shaldnikita.notifier.port.adapter.dao.entities.UserTable
import ru.shaldnikita.notifier.port.adapter.dao.entities.{User, UserTable}
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.04.2019
  */
class UserRepository(db: Database) extends TableQuery(new UserTable(_)) {

  private val users = TableQuery[UserTable]

  def find(userId: String): Future[Option[User]] = {
    db.run(users.filter(_.userId === userId).result.headOption)
  }

}
