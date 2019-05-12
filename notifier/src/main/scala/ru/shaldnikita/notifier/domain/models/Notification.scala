package ru.shaldnikita.notifier.domain.models

import java.util.UUID

import ru.shaldnikita.notifier.domain.models.users.UserId

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
case class Notification(notificationId: NotificationId,
                        payload: NotificationPayload,
                        notifyIn: FiniteDuration,
                        additionalInfo: additionalInfo,
                        userId: UserId)

case class additionalInfo(isWholeDay: Boolean,
                          isRepeatable: Boolean,
                          repeatIn: Option[FiniteDuration])

case class NotificationId(id: String = UUID.randomUUID().toString)

case class NotificationPayload(text: Option[String],
                               data: Option[Array[Byte]])