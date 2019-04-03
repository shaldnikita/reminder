package ru.shaldnikita.timer.actors

import akka.actor.{Actor, ActorLogging}
import ru.shaldnikita.timer.entities.Notification
import ru.shaldnikita.timer.messages.NotifyMessage
import ru.shaldnikita.timer.notificators.Notificator
import ru.shaldnikita.timer.service.UserService

import scala.async.Async._
import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
class Notifier(notifiers: List[Notificator], userService: UserService)(implicit ec: ExecutionContext) extends Actor with ActorLogging {
  override def receive = {
    case notify: NotifyMessage => notifyUser(notify.notification)

  }

  private def notifyUser(notification: Notification)(implicit ec: ExecutionContext): Future[Unit] = async {
    val owner = userService.find(notification.owner.id)
    notifiers.foreach(_.notify(owner))
  }
}
