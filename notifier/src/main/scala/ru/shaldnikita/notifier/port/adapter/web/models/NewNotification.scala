package ru.shaldnikita.notifier.port.adapter.web.models

import spray.json.RootJsonFormat

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */

case class NewNotification(text: String,
                           notifyIn: FiniteDuration,
                           isWholeDay: Boolean,
                           isRepeatable: Boolean,
                           repeatIn: Option[FiniteDuration])

object NewNotification extends WebModel[NewNotification] {
  def jsonFormat: RootJsonFormat[NewNotification] = jsonFormat5(NewNotification.apply)
}
