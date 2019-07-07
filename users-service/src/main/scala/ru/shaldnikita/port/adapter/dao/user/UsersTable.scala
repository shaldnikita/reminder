package ru.shaldnikita.port.adapter.dao.user

import ru.shaldnikita.domain.models.users.User
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UsersTable(tag: Tag) extends Table[UserSchema](tag, "users") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  override def * =
    (userId, firstName, secondName) <> (UserSchema.tupled, UserSchema.unapply)

  def userId = column[String]("user_id", O.Unique)

  def firstName = column[String]("first_name")

  def secondName = column[String]("second_name")

  def user_id_udx = index("users_id_udx", userId, unique = true)
}

case class UserSchema(userId: String, firstName: String, secondName: String) {
  def toDomain: User = {
    User(
      userId,
      firstName,
      secondName,
      Nil
    )
  }
}
