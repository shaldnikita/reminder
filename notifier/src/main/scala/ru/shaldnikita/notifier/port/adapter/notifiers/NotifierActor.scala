package ru.shaldnikita.notifier.port.adapter.notifiers

import akka.actor.{Actor, ActorLogging}
import ru.shaldnikita.notifier.domain.messages.{NotificationContent, NotifyMessage, User}

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.04.2019
  */
abstract class NotifierActor(implicit ec: ExecutionContext) extends Actor with ActorLogging {

  override def receive = {
    case NotifyMessage(user, content) => notify(user, content)
    case _: AnyRef => log.warning("Received wrong message {}", _)
  }

  abstract def notify(user: User, notification: NotificationContent)(implicit ec: ExecutionContext): Future[Unit]

  abstract def notificationType: NotifierType
}
