package ru.shaldnikita.notifier.actors.notifiers

import akka.actor.{Actor, ActorLogging}
import ru.shaldnikita.notifier.domain.exceptions.{NotificationFailedException, UserNotFoundException}
import ru.shaldnikita.notifier.domain.messages.NotifyMessage
import ru.shaldnikita.notifier.port.adapter.notifiers.NotifierActor
import ru.shaldnikita.notifier.service.UserService

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
class NotifyManager(notifiers: List[NotifierActor], userService: UserService)(
  implicit ec: ExecutionContext) extends Actor with ActorLogging {

  override def receive = {
    case notify: NotifyMessage =>
      val notificationResult = notifyUser(notify)
      notificationResult.onComplete {
        case Success(v) =>
        case Failure(e: NotificationFailedException) => NotificationFailedMessage()
      }

    case other: AnyRef => log.warning("Unexpected message {}", other)
  }

  private def notifyUser(notify: NotifyMessage)(
    implicit ec: ExecutionContext): Future[Unit] =
    for {
      maybeOwner <- userService.find(notify.user.userId)
      owner = maybeOwner.getOrElse(
        throw new UserNotFoundException(
          s"Unable to notify user ${notify.user.userId}. User does not exist."))
    } yield Future.traverse(notifiers) { notifier =>
      notifier.notify(owner, notify)
    }
}

object NotifyManager {
  val name = "notifier"
}
