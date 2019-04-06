package ru.shaldnikita.notifier.dao.entities

import java.util.UUID

import slick.jdbc.H2Profile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
case class User(email: String,
                firstName: String,
                secondName: String,
                phoneNumber: String,
                userId: String = UUID.randomUUID().toString)

class UserTable(tag: Tag) extends Table[User](tag, "users") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def userId = column[String]("user_id")

  def email = column[String]("email")

  def firstName = column[String]("first_name")

  def secondName = column[String]("second_name")

  def phoneNumber = column[String]("phone_number")

  override def * =
    (userId, email, firstName, secondName, phoneNumber) <> (User.tupled, User.unapply)

  def user_id_idx = index("users_used_id_udx", userId, unique = true)

  def phone_number_idx =
    index("users_phone_number_idx", phoneNumber, unique = true)

  def email_idx = index("users_email_idx", email, unique = true)
}
