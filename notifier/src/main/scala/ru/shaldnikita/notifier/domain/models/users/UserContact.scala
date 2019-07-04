package ru.shaldnikita.notifier.domain.models.users

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 12.05.2019
  */
sealed case class UserContact(value: String, contactType: UserContactType)

object UserContact {

  case class Email(email: String) extends UserContact(email, UserContactType.Email)

  case class PhoneNumber(phoneNumber: String) extends UserContact(phoneNumber, UserContactType.Email)

  case class VkPage(link: String) extends UserContact(link, UserContactType.VkPage)

}


