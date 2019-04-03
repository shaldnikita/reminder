package ru.shaldnikita.timer.dao.entities

/*import slick.jdbc.H2Profile.api._
/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
class User(tag: Tag) extends Table[(String, String, String, String, String)](tag, "users") {

  def id = column[Long]("id", O.PrimaryKey)

  def userId = column[String]("user_id")

  def email = column[String]("email")

  def firstName = column[String]("first_name")

  def secondName = column[String]("second_name")

  def phoneNumber = column[String]("phone_number")

  override def * = (userId, email, firstName, secondName, phoneNumber)
}*/

case class User(userId: String, email: String)
