package port.adapter.dao.user

import port.adapter.dao.Tables.users
import slick.jdbc.H2Profile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserDao(db: Database) {

  def createUser(user: UserSchema) = users += user

  def findUser(userId: String) = users.filter(_.userId === userId).result

  def updateUser(user: UserSchema) = users.filter(_.userId === user.userId).update(user)

  def deleteUser(userId: String) = users.filter(_.userId === userId).delete

}
