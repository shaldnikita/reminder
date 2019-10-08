package ru.shaldnikita.users.domain.models.contacts

import enumeratum._

//enum
sealed abstract class ContactType(val id: String) extends EnumEntry

object ContactType extends Enum[ContactType] {

  def ofId(id: String): Option[ContactType] = values.find(_.id == id)

  override def values = findValues

  case object Email extends ContactType("email")

  case object PhoneNumber extends ContactType("phone_number")

  case object Vk extends ContactType("vk")

  case object Telegram extends ContactType("telegram")

}
