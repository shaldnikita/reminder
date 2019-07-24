package ru.shaldnikita.port.adapter.dao.user

import ru.shaldnikita.port.adapter.dao.Tables.users
import ru.shaldnikita.port.adapter.dao.contact.ContactDao
import ru.shaldnikita.port.adapter.dao.user.UserDao._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserDao {

  def create(user: UserSchema)(implicit db: Database): Future[Int] =
    db.run(CreateUser(user))

  def findOne(
      userId: String
  )(implicit db: Database): Future[Option[UserSchema]] =
    db.run(FindUser(userId))

  def findAll()(implicit db: Database): Future[Seq[UserSchema]] =
    db.run(FindUsers)

  def update(user: UserSchema)(implicit db: Database): Future[Int] =
    db.run(UpdateUser(user))

  def deleteUserAndContacts(
      userId: String
  )(implicit db: Database, ec: ExecutionContext): Future[Int] = {
    val actions = for {
      deleted <- DeleteUser(userId)
      _       <- ContactDao.RemoveAllUserContacts(userId)
    } yield deleted
    db.run(actions.transactionally)
  }
}

object UserDao {
  val CreateUser = (user: UserSchema) => users += user

  val FindUser = (userId: String) =>
    users.filter(_.userId === userId).result.headOption

  val FindUsers = users.result

  val UpdateUser = (user: UserSchema) =>
    users.filter(_.userId === user.userId).update(user)

  val DeleteUser = (userId: String) => users.filter(_.userId === userId).delete

}
