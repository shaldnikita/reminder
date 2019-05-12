package ru.shaldnikita.notifier.application

import ru.shaldnikita.notifier.domain.exceptions.{NotificationNotFoundException, UserNotFoundException}
import ru.shaldnikita.notifier.domain.notifiers.Notifier
import ru.shaldnikita.notifier.port.adapter.dao.repositories.{NotificationRepository, UserRepository}

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
class NotifyManager(notifiers: Seq[Notifier],
                    userRepository: UserRepository,
                    notificationRepository: NotificationRepository)(
                     implicit ec: ExecutionContext) {

  def notifyUser(userId: String, notificationId: String)(
    implicit ec: ExecutionContext): Future[Unit] =
    for {
      maybeOwner <- userRepository.find(userId)
      owner = maybeOwner.getOrElse(
        throw new UserNotFoundException(userId))

      maybeNotification <- notificationRepository.findByNotificationId(notificationId)
      notification = maybeNotification.getOrElse(throw new NotificationNotFoundException(notificationId))
    } yield Future.traverse(notifiers) { notifier =>
      notifier.notify(owner, notification)
    }
}

