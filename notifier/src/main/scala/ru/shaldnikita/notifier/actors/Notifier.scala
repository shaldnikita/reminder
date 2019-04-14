package ru.shaldnikita.notifier.actors

import akka.actor.{Actor, ActorLogging}
import cats.implicits._
import ru.shaldnikita.notifier.entities.Notification
import ru.shaldnikita.notifier.exceptions.UserNotFoundException
import ru.shaldnikita.notifier.messages.NotifyMessage
import ru.shaldnikita.notifier.notificators.Notificator
import ru.shaldnikita.notifier.service.UserService

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
class Notifier(notifiers: List[Notificator], userService: UserService)(
  implicit ec: ExecutionContext)
  extends Actor
    with ActorLogging {
  override def receive = {
    case notify: NotifyMessage =>
      val res = for {
        res <- notifyUser(notify.notification)
      } yield res
      sender() ! "OK"
  }

  private def notifyUser(notification: Notification)(
    implicit ec: ExecutionContext): Future[Unit] =
    for {
      maybeOwner <- userService.find(notification.owner.id)
      owner = maybeOwner.getOrElse(
        throw new UserNotFoundException(
          s"Unable to notify user ${notification.owner.id}. User does not exist."))
    } yield notifiers.map(_.notify(owner)).sequence_
}
