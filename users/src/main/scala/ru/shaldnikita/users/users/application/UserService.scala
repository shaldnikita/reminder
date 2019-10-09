package ru.shaldnikita.users.users.application

import java.util.UUID

import ru.shaldnikita.users.application.exceptions.UserNotFoundException
import ru.shaldnikita.users.application.models.users.CreateUser
import ru.shaldnikita.users.domain.models.users.User
import ru.shaldnikita.users.port.adapter.dao.contact.ContactDaoImpl
import ru.shaldnikita.users.port.adapter.dao.user.UserDaoImpl
import ru.shaldnikita.users.users.application.exceptions.UserNotFoundException
import ru.shaldnikita.users.users.application.models.users.CreateUser
import ru.shaldnikita.users.users.domain.models.users.User
import ru.shaldnikita.users.users.port.adapter.dao.contact.ContactDaoImpl
import ru.shaldnikita.users.users.port.adapter.dao.user.UserDaoImpl
import slick.jdbc.PostgresProfile.api._
import zio.{Task, ZIO}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserService(userDao: UserDaoImpl,
                  contactDao: ContactDaoImpl,
                  db: Database) {

  def findUser(userId: String): Task[User] = {
    for {
      contacts <- contactDao.findUserContacts(userId).provide(db)
      user     <- userDao.findOne(userId).provide(db)
    } yield {
      user
        .map(_.toDomain)
        .map(_.copy(contacts = contacts.map(_.toDomain).toList))
    }
  }.flatMap {
    case None    => ZIO.fail(UserNotFoundException(userId))
    case Some(v) => ZIO.succeed(v)
  }

  def findUsers(): Task[Seq[User]] =
    userDao.findAll().provide(db).map(_.map(_.toDomain))

  def updateUser(user: User): Task[User] = {
    userDao
      .update(user.toSchema)
      .provide(db)
      .flatMap {
        case 0 => ZIO.fail(UserNotFoundException(user.userId))
        case 1 => ZIO.succeed(user)
      }
  }

  def createUser(user: CreateUser): Task[User] = {
    import user._
    val createdUser =
      User(UUID.randomUUID().toString, firstName, secondName, contacts)
    userDao
      .create(createdUser.toSchema)
      .provide(db)
      .map(_ => createdUser)
  }

  def deleteUser(userId: String): Task[Unit] =
    userDao
      .deleteUserAndContacts(userId)
      .provide(db)
      .flatMap {
        case 0 => ZIO.fail(UserNotFoundException(userId))
        case 1 => ZIO.succeed(())
      }
}
