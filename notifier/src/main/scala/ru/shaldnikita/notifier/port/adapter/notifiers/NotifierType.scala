package ru.shaldnikita.notifier.port.adapter.notifiers

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
abstract case class NotifierType(name: String)

case object EmailNotifier extends NotifierType("Email notification")
case object SmsNotifier extends NotifierType("Sms notification")
