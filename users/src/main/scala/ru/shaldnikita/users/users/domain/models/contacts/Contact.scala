package ru.shaldnikita.users.users.domain.models.contacts

import ru.shaldnikita.users.users.domain.models.contacts.Contact.ContactId
import ru.shaldnikita.users.users.domain.models.users.User.UserId

sealed abstract case class Contact(contactId: ContactId,
                                   `type`: ContactType,
                                   value: String,
                                   userId: UserId)

object Contact {

  case class ContactId(id: String) extends AnyVal

  def apply(contactId: ContactId,
            `type`: ContactType,
            value: String,
            userId: UserId): Contact = {
    `type` match {
      case ContactType.Vk          => new VkPage(value, userId, contactId)
      case ContactType.Telegram    => new Telegram(value, userId, contactId)
      case ContactType.PhoneNumber => new PhoneNumber(value, userId, contactId)
      case ContactType.Email       => new Email(value, userId, contactId)
    }
  }
  final class VkPage(login: String, userId: UserId, contactId: ContactId)
      extends Contact(contactId, ContactType.Vk, login, userId)

  final class Telegram(login: String, userId: UserId, contactId: ContactId)
      extends Contact(contactId, ContactType.Telegram, login, userId)

  final class PhoneNumber(number: String, userId: UserId, contactId: ContactId)
      extends Contact(contactId, ContactType.PhoneNumber, number, userId)

  final class Email(email: String, userId: UserId, contactId: ContactId)
      extends Contact(contactId, ContactType.Email, email, userId)
}
