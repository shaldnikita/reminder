package ru.shaldnikita.users.users.port.adapter.dao.user

import ru.shaldnikita.users.DBTask
import ru.shaldnikita.users.port.adapter.dao.Tables.users
import ru.shaldnikita.users.port.adapter.dao._
import ru.shaldnikita.users.port.adapter.dao.user.UserDaoImpl._
import ru.shaldnikita.users.users.port.adapter.dao.contact.ContactDaoImpl
import slick.jdbc.PostgresProfile.api._
import zio.ZIO

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserDaoImpl {

  def create(user: UserSchema): DBTask[Int] =
    ZIO.accessM[Database](_.runZ(CreateUser(user)))

  def findOne(userId: String): DBTask[Option[UserSchema]] =
    ZIO.accessM[Database](_.runZ(FindUser(userId)))

  def findAll(): DBTask[Seq[UserSchema]] =
    ZIO.accessM[Database](_.runZ(FindUsers))

  def update(user: UserSchema): DBTask[Int] =
    ZIO.accessM[Database](_.runZ(UpdateUser(user)))

  def deleteUserAndContacts(userId: String): DBTask[Int] =
    for {
      db <- ZIO.environment[Database]
      result <- ZIO.fromFuture { implicit ec =>
        val actions = for {
          deleted <- DeleteUser(userId)
          _       <- ContactDaoImpl.RemoveAllUserContacts(userId)
        } yield deleted
        db.run(actions.transactionally)
      }
    } yield result
}

object UserDaoImpl {
  val CreateUser = (user: UserSchema) => users += user

  val FindUser = (userId: String) =>
    users.filter(_.userId === userId).result.headOption

  val FindUsers = users.result

  val UpdateUser = (user: UserSchema) =>
    users.filter(_.userId === user.userId).update(user)

  val DeleteUser = (userId: String) => users.filter(_.userId === userId).delete

}
