package domain.users.contacts

import enumeratum._

//enum
sealed abstract class ContactType(id: String) extends EnumEntry

object ContactType extends Enum[ContactType] {

  override def values = findValues

  case object Email extends ContactType("email")

  case object PhoneNumber extends ContactType("phone_number")

  case object Vk extends ContactType("vk")

  case object Telegram extends ContactType("telegram")

}
