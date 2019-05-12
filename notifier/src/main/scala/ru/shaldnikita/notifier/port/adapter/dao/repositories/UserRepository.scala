package ru.shaldnikita.notifier.port.adapter.dao.repositories

import ru.shaldnikita.notifier.domain.models.{User, UserId}
import ru.shaldnikita.notifier.port.adapter.dao.Tables.users
import ru.shaldnikita.notifier.port.adapter.dao.tables.UserTable
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.04.2019
  */
class UserRepository(db: Database) extends TableQuery(new UserTable(_)) {

  def find(userId: UserId): Future[Option[User]] = {
    db.run(users.filter(_.userId === userId.id).result.headOption)
  }

}
