package ru.shaldnikita.notifier.domain.exceptions

import ru.shaldnikita.notifier.domain.notifiers.NotificationType

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
final case class NotificationFailedException(notificationId: String,
                                             userId: String,
                                             notificationType: NotificationType,
                                             originalException: Option[Throwable])
  extends RuntimeException(s"Unable to notify user [$userId] " +
    s"with notification [$notificationId] via [$notificationType].")