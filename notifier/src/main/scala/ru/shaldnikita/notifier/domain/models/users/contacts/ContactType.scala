package ru.shaldnikita.notifier.domain.models.users.contacts


//enum
sealed abstract class ContactType(id: String) extends EnumEntry

object ContactType extends Enum[ContactType] {

  case object Email extends ContactType("email")

  case object PhoneNumber extends ContactType("phone_number")

  case object Vk extends ContactType("vk")

  case object Telegram extends ContactType("telegram")

}
