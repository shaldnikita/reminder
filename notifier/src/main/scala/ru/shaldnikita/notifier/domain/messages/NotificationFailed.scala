package ru.shaldnikita.notifier.domain.messages

import akka.actor.ActorRef
import ru.shaldnikita.notifier.port.adapter.notifiers.NotifierType

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
case class NotificationFailed(notification: NotificationContent,
                              user: User,
                              notifierType: NotifierType,
                              notifier: ActorRef,
                              cause: Throwable)
