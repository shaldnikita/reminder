package ru.shaldnikita.notifier.domain.messages

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
case class NotifyMessage(user: User,
                         content: NotificationContent)

case class User(email: String,
                firstName: String,
                secondName: String,
                phoneNumber: String,
                userId: String)

case class NotificationContent(text: String)