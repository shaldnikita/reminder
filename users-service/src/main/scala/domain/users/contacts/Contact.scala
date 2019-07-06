package domain.users.contacts

import java.util.UUID

sealed abstract case class Contact(contactId: String,
                                   `type`: ContactType,
                                   value: String,
                                   userId: String)

object Contact {

  def apply(`type`: ContactType,
            value: String,
            userId: String,
            contactId: Option[String]): Contact = {
    val id = contactId.getOrElse(UUID.randomUUID().toString)
    `type` match {
      case ContactType.Vk          => new VkPage(value, userId, id)
      case ContactType.Telegram    => new Telegram(value, userId, id)
      case ContactType.PhoneNumber => new PhoneNumber(value, userId, id)
      case ContactType.Email       => new Email(value, userId, id)
    }
  }
  final class VkPage(login: String, userId: String, contactId: String)
      extends Contact(contactId, ContactType.Vk, login, userId)

  final class Telegram(login: String, userId: String, contactId: String)
      extends Contact(contactId, ContactType.Telegram, login, userId)

  final class PhoneNumber(number: String, userId: String, contactId: String)
      extends Contact(contactId, ContactType.PhoneNumber, number, userId)

  final class Email(email: String, userId: String, contactId: String)
      extends Contact(contactId, ContactType.Email, email, userId)
}
