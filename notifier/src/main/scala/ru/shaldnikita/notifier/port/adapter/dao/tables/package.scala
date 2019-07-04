package ru.shaldnikita.notifier.port.adapter.dao

import slick.jdbc.H2Profile.api._

import scala.concurrent.duration.{Duration, FiniteDuration}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 12.05.2019
  */
package object tables {

  implicit val finiteDurationColumnType = MappedColumnType.base[FiniteDuration, String](
    fd => fd.toString, str => FiniteDuration(Duration(str)._1, Duration(str)._2))
}
