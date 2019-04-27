package ru.shaldnikita.notifier.port.adapter.web.models

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
case class Notification(text: String,
                        notifyIn: FiniteDuration,
                        isWholeDay: Boolean,
                        isRepeatable: Boolean,
                        repeatIn: Option[FiniteDuration],
                        id: Option[String])

object Notification extends WebModel[Notification] {
  override def jsonFormat = jsonFormat6(Notification.apply)
}