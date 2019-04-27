package ru.shaldnikita.notifier.port.adapter.dao.entities

import ru.shaldnikita.notifier.domain.entities.User
import slick.jdbc.H2Profile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */

class UserTable(tag: Tag) extends Table[User](tag, "users") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def userId = column[String]("user_id")

  def email = column[String]("email")

  def firstName = column[String]("first_name")

  def secondName = column[String]("second_name")

  def phoneNumber = column[String]("phone_number")

  override def * =
    (email, firstName, secondName, phoneNumber, userId) <> (User.tupled, User.unapply)

  def user_id_idx = index("users_used_id_udx", userId, unique = true)

  def phone_number_idx =
    index("users_phone_number_idx", phoneNumber, unique = true)

  def email_idx = index("users_email_idx", email, unique = true)
}
