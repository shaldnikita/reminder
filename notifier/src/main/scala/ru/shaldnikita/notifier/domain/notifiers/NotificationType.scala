package ru.shaldnikita.notifier.domain.notifiers

import enumeratum._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */

sealed abstract class NotificationType(description: String) extends EnumEntry

object NotificationType extends Enum[NotificationType] {

  override def values = findValues

  case object EmailNotification extends NotificationType("Email notification")

  case object SmsNotification extends NotificationType("Sms notification")

  case object VkMessageNotification extends NotificationType("VK message notification")

  case object VkCallNotification extends NotificationType("VK call notification")

}

