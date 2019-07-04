package ru.shaldnikita.notifier.domain.notifiers


import ru.shaldnikita.notifier.domain.models.NotificationPayload
import ru.shaldnikita.notifier.domain.models.users.UserContactType

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 12.05.2019
  */
abstract class Notifier {

  def notify(contacts: Seq[UserContactType],
             notificationPayload: NotificationPayload)(implicit ec: ExecutionContext): Future[Unit] = {
    val requiredContactsForNotification = contacts.filter(requiredContactTypes.contains(_))
    doNotify(requiredContactsForNotification, notificationPayload)
  }

  def doNotify(contacts: Seq[UserContactType],
               notificationPayload: NotificationPayload)(implicit ec: ExecutionContext): Future[Unit]

  def requiredContactTypes[T <: UserContactType.type]: Seq[T]

  def notificationType: NotificationType
}
