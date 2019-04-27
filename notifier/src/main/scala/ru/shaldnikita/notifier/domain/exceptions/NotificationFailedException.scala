package ru.shaldnikita.notifier.domain.exceptions

import ru.shaldnikita.notifier.domain.entities.{Notification, User}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class NotificationFailedException(notification: Notification, user: User) {

}
