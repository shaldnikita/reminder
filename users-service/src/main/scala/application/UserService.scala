package application

import application.Application._
import domain.users.User
import port.adapter.dao.contact.ContactDao
import port.adapter.dao.user.UserDao
import slick.jdbc.H2Profile.api._

import scala.concurrent.{ExecutionContext => EC}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserService(userDao: UserDao, contactDao: ContactDao)(implicit ec: EC,
                                                            db: Database) {

  def findUser(userId: String) = {
    for {
      contacts <- contactDao.findUserContacts(userId)
      user     <- userDao.findOne(userId)
    } yield {
      user
        .map(_.toDomain)
        .map(_.copy(contacts = contacts.map(_.toDomain).toList))
    }
  }

  def updateUser(user: User) = userDao.update(user.toSchema)

  def createUser(user: User) = userDao.create(user.toSchema)

  def deleteUser(userId: String) = userDao.deleteUserAndContacts(userId)
}
