package ru.shaldnikita.users.users.application

import ru.shaldnikita.users.users.application.exceptions.UserNotFoundException
import ru.shaldnikita.users.users.application.models.users.CreateUser
import ru.shaldnikita.users.users.domain.models.users.User
import ru.shaldnikita.users.users.domain.models.users.User.UserId
import ru.shaldnikita.users.users.domain.repositories.{
  ContactRepository,
  UserRepository
}
import ru.shaldnikita.users.users.port.adapter.dao.contact.ContactDaoImpl
import ru.shaldnikita.users.users.port.adapter.dao.user.UserDaoImpl
import zio.{RIO, ZIO}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserService(userDao: UserDaoImpl, contactDao: ContactDaoImpl) {

  def findUser(
      userId: UserId): RIO[ContactRepository with UserRepository, User] =
    for {
      contacts <- ZIO.accessM[ContactRepository](
        _.contactRepository.findUserContacts(userId))
      user <- ZIO
        .accessM[UserRepository](_.userRepository.findOne(userId))
        .someOrFail(UserNotFoundException(userId))
    } yield {
      user.copy(contacts = contacts.toList)
    }

  def findUsers(): RIO[UserRepository, Seq[User]] =
    ZIO.accessM[UserRepository](_.userRepository.findAll())

  def updateUser(user: User): RIO[UserRepository, User] =
    ZIO
      .accessM[UserRepository](_.userRepository.update(user))
      .flatMap {
        case 0 => ZIO.fail(UserNotFoundException(user.userId))
        case 1 => ZIO.succeed(user)
      }

  def createUser(user: CreateUser): RIO[UserRepository, User] = {
    import user._
    val createdUser =
      User(UserId(), firstName, secondName, contacts)
    ZIO
      .accessM[UserRepository](_.userRepository.create(createdUser))
      .map(_ => createdUser)
  }

  def deleteUser(userId: UserId): RIO[UserRepository, Unit] =
    ZIO
      .accessM[UserRepository] { r =>
        for {
          _ <- r.userRepository
            .findOne(userId)
            .someOrFail(UserNotFoundException(userId))
          _ <- r.userRepository.deleteUserAndContacts(userId)
        } yield ()
      }

}
