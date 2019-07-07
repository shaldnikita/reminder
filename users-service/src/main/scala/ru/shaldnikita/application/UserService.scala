package ru.shaldnikita.application

import cats.implicits._
import ru.shaldnikita.application.Application._
import ru.shaldnikita.domain.users.User
import ru.shaldnikita.port.adapter.dao.contact.ContactDao
import ru.shaldnikita.port.adapter.dao.user.UserDao
import slick.jdbc.H2Profile.api._

import scala.concurrent.{Future, ExecutionContext => EC}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserService(userDao: UserDao, contactDao: ContactDao)(implicit ec: EC,
                                                            db: Database) {

  def findUser(userId: String): Future[Option[User]] = {
    for {
      contacts <- contactDao.findUserContacts(userId)
      user     <- userDao.findOne(userId)
    } yield {
      user
        .map(_.toDomain)
        .map(_.copy(contacts = contacts.map(_.toDomain).toList))
    }
  }

  def findUsers(): Future[Seq[User]] = userDao.findAll().map(_.map(_.toDomain))

  def updateUser(user: User): Future[Option[User]] = {
    userDao
      .update(user.toSchema)
      .map {
        case 0 => None
        case 1 => user.some
      }
  }

  def createUser(user: User): Future[User] = {
    userDao
      .create(user.toSchema)
      .map(_ => user)
  }

  //TODO replace unit with option
  def deleteUser(userId: String): Future[Unit] =
    userDao.deleteUserAndContacts(userId)
}
