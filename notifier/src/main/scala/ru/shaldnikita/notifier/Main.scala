package ru.shaldnikita.notifier

import java.util.concurrent.TimeUnit

import ru.shaldnikita.notifier.entities.{Notification, Owner}

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 02.04.2019
  */
object Main extends App {

  override def main(args: Array[String]): Unit = {
    Environment

    Environment.notifyKeeper ! Notification(
      text = "asd",
      notifyIn = FiniteDuration.apply(1, TimeUnit.SECONDS),
      isWholeDay = false,
      isRepeatable = false,
      repeatIn = None,
      owner = Owner("Ya")
    )
  }

}