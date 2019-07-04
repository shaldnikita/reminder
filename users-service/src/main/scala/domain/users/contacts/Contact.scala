package domain.users.contacts

import java.util.UUID

sealed abstract case class Contact(`type`: ContactType,
                                   value: String,
                                   userId: String,
                                   contactId: String = UUID.randomUUID().toString)

object Contact {

  final class VkPage(login: String, userId: String) extends Contact(ContactType.Vk, login, userId)

  final class Telegram(login: String, userId: String) extends Contact(ContactType.Telegram, login, userId)

  final class PhoneNumber(number: String, userId: String) extends Contact(ContactType.PhoneNumber, number, userId)

  final class Email(email: String, userId: String) extends Contact(ContactType.Email, email, userId)

}
