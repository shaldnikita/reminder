package ru.shaldnikita.notifier.domain.messages

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
case class CreateNotification(text: String,
                              notifyIn: FiniteDuration,
                              isWholeDay: Boolean,
                              isRepeatable: Boolean,
                              repeatIn: Option[FiniteDuration])