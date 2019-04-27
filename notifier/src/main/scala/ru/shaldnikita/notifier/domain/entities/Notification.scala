package ru.shaldnikita.notifier.domain.entities

import java.util.UUID

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
case class Notification(notificationId: String = UUID.randomUUID().toString,
                        text: String,
                        notifyIn: FiniteDuration,
                        isWholeDay: Boolean,
                        isRepeatable: Boolean,
                        repeatIn: Option[FiniteDuration],
                        userId: String)