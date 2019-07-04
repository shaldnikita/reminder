package ru.shaldnikita.notifier.domain.models.users.contacts

sealed trait Contact {
  def `type`: ContactType

  def identifier: String
}

object Contact {

  final class VkPage(login: String) extends Contact {
    override def `type`: ContactType = ContactType.Vk

    override def identifier: String = login
  }

  final class Telegram(login: String) extends Contact {
    override def `type`: ContactType = ContactType.Telegram

    override def identifier: String = login
  }

  final class PhoneNumber(number: String) extends Contact {
    override def `type`: ContactType = ContactType.PhoneNumber

    override def identifier: String = number
  }

  final class Email(email: String) extends Contact {
    override def `type`: ContactType = ContactType.Email

    override def identifier: String = email
  }

}
