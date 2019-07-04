package ru.shaldnikita.notifier.domain.models.notifications

import enumeratum._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */

sealed abstract class NotificationType(id: String, description: String) extends EnumEntry

object NotificationType extends Enum[NotificationType] {

  override def values = findValues

  case object Email extends NotificationType("email", "Email notification")

  case object Sms extends NotificationType("sms", "Sms notification")

  case object VkMessage extends NotificationType("vk_message","VK message notification")

  case object VkCall extends NotificationType("vk_call","VK call notification")

  case object TelegramMessage extends NotificationType("telegram_message","Telegram message notification")

}

