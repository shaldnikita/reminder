package ru.shaldnikita.notifier.actors.notifiers

import akka.actor.{Actor, ActorLogging}
import ru.shaldnikita.notifier.domain.messages.{NotificationFailed, NotifyMessage}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class NotifiersSupervisor() extends Actor with ActorLogging {

  override def preStart() = {
    super.preStart()
    notifiers.foreach(context.watch)
  }

  override def receive = {
    case NotificationFailed(notification, user, notifierType, notifier, cause) =>
      log.warning(s"Notification $notification via ${notifierType.name} failed for user ${user.userId} cause of $cause")
      notifier ! NotifyMessage(user, notification)

    case _: AnyRef => log.warning("Received wrong message {}", _)
  }
}
