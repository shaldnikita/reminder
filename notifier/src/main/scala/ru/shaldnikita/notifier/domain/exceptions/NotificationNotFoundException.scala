package ru.shaldnikita.notifier.domain.exceptions

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 12.05.2019
  */
final class NotificationNotFoundException(notificationId: String)
  extends RuntimeException(s"Notification $notificationId does not exist")