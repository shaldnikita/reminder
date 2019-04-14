package ru.shaldnikita.notifier.actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import ru.shaldnikita.notifier.entities.Notification
import ru.shaldnikita.notifier.messages.NotifyMessage

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import akka.pattern.ask

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 02.04.2019
  */
class NotifyKeeper(notifier: ActorRef)(implicit ec: ExecutionContext) extends Actor with ActorLogging {
  private val notifyMessages: mutable.Map[String, Notification] = mutable.HashMap.empty

  override def receive = {
    case message: Notification => handlerMessage(message)
    case other: AnyRef => log.warning("Wrong message {}", other)
  }

  private def handlerMessage(message: Notification) = {
    if (message.isRepeatable) {
      context.system.scheduler.schedule(message.repeatIn.get, message.repeatIn.get) {
        notifier ! Notification
      }
    } else {
      context.system.scheduler.scheduleOnce(message.notifyIn) {
        notifier ! NotifyMessage(message)
      }
    }
    notifyMessages.put(message.id, message)
  }
}

object NotifyKeeper {
  def name: String = "notify-keeper"
}
