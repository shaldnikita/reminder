package ru.shaldnikita.notifier.domain.messages

import java.util.UUID

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
case class NotificationMsg(text: String,
                           notifyIn: FiniteDuration,
                           isWholeDay: Boolean,
                           isRepeatable: Boolean,
                           repeatIn: Option[FiniteDuration],
                           id: Option[UUID])