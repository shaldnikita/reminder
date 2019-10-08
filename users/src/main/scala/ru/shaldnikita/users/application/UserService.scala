package ru.shaldnikita.users.application

import java.util.UUID

import ru.shaldnikita.users.application.exceptions.UserNotFoundException
import ru.shaldnikita.users.application.models.users.CreateUser
import ru.shaldnikita.users.domain.models.users.User
import ru.shaldnikita.users.port.adapter.dao.contact.ContactDao
import ru.shaldnikita.users.port.adapter.dao.user.UserDao
import ru.shaldnikita.users.port.adapter.dao.user.UserDao
import ru.shaldnikita.users.application.exceptions.UserNotFoundException
import ru.shaldnikita.users.application.models.users.CreateUser
import ru.shaldnikita.users.domain.models.users.User
import ru.shaldnikita.users.port.adapter.dao.contact.ContactDao
import ru.shaldnikita.users.port.adapter.dao.user.UserDao
import ru.shaldnikita.users.port.adapter.dao.contact.ContactDao
import ru.shaldnikita.users.port.adapter.dao.user.UserDao
import ru.shaldnikita.users.application.exceptions.UserNotFoundException
import ru.shaldnikita.users.application.models.users.CreateUser
import ru.shaldnikita.users.domain.models.users.User
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{Future, ExecutionContext => EC}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserService(userDao: UserDao, contactDao: ContactDao)(implicit ec: EC,
                                                            db: Database) {

  def findUser(userId: String): Future[User] = {
    for {
      contacts <- contactDao.findUserContacts(userId)
      user     <- userDao.findOne(userId)
    } yield {
      user
        .map(_.toDomain)
        .map(_.copy(contacts = contacts.map(_.toDomain).toList))
    }
  }.flatMap {
    case None    => Future.failed(UserNotFoundException(userId))
    case Some(v) => Future.successful(v)
  }

  def findUsers(): Future[Seq[User]] = userDao.findAll().map(_.map(_.toDomain))

  def updateUser(user: User): Future[User] = {
    userDao
      .update(user.toSchema)
      .flatMap {
        case 0 => Future.failed(UserNotFoundException(user.userId))
        case 1 => Future.successful(user)
      }
  }

  def createUser(user: CreateUser): Future[User] = {
    import user._
    val createdUser =
      User(UUID.randomUUID().toString, firstName, secondName, contacts)
    userDao
      .create(createdUser.toSchema)
      .map(_ => createdUser)
  }

  def deleteUser(userId: String): Future[Unit] =
    userDao
      .deleteUserAndContacts(userId)
      .flatMap {
        case 0 => Future.failed(UserNotFoundException(userId))
        case 1 => Future.successful(())
      }
}
