package ru.shaldnikita.users.domain.models.contacts

sealed abstract case class Contact(contactId: String,
                                   `type`: ContactType,
                                   value: String,
                                   userId: String)

object Contact {

  def apply(contactId: String,
            `type`: ContactType,
            value: String,
            userId: String): Contact = {
    `type` match {
      case ContactType.Vk          => new VkPage(value, userId, contactId)
      case ContactType.Telegram    => new Telegram(value, userId, contactId)
      case ContactType.PhoneNumber => new PhoneNumber(value, userId, contactId)
      case ContactType.Email       => new Email(value, userId, contactId)
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
