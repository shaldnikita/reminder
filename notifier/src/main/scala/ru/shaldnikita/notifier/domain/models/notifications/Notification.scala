package ru.shaldnikita.notifier.domain.models.notifications

import java.util.UUID

import ru.shaldnikita.notifier.domain.models.users.UserId

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
case class NotificationId(id: String = UUID.randomUUID().toString)

case class Notification(notificationId: NotificationId,
                        userId: UserId,
                        payload: NotificationPayload,
                        notifyIn: FiniteDuration,
                        additionalInfo: AdditionalInfo)

case class AdditionalInfo(isWholeDay: Boolean,
                          isRepeatable: Boolean,
                          repeatIn: Option[FiniteDuration])


case class NotificationPayload(text: Option[String], data: Option[Array[Byte]])
