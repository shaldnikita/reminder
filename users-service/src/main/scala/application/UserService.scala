package application

import application.Application._
import cats.implicits._
import domain.users.User
import port.adapter.dao.contact.ContactDao
import port.adapter.dao.user.UserDao
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserService(userDao: UserDao,
                  contactDao: ContactDao,
                  db: Database) {

  def findUser(userId: String) = db.run(userDao.findUser(userId).headOption)

  def updateUser(user: User) = db.run(userDao.updateUser(user.toSchema))

  def createUser(user: User): Future[Unit] = {
    val createUserAction = userDao.createUser(user.toSchema)
    val actions = user.contacts.map(_.toSchema).toList.toNel
      .map(contacts => DBIO.seq(
        createUserAction,
        contactDao.insert(contacts),
      ))
      .getOrElse(DBIO.seq(createUserAction))

    db.run(actions.transactionally)
  }

  def deleteUser(userId: String) = db.run(
    DBIO.seq(
      userDao.deleteUser(userId),
      contactDao.removeAllUserContacts(userId)
    ).transactionally)
}
