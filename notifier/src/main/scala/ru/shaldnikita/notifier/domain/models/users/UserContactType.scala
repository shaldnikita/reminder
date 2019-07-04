package ru.shaldnikita.notifier.domain.models.users

import enumeratum._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 12.05.2019
  */
sealed abstract class UserContactType(description: String) extends EnumEntry

object UserContactType extends Enum[UserContactType] {

  override def values = findValues

  case object Email extends UserContactType("Email address")

  case object PhoneNumber extends UserContactType("Phone number")

  case object VkPage extends UserContactType("Vk.com page")

}
