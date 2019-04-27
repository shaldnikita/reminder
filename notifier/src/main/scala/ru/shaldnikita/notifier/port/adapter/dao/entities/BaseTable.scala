package ru.shaldnikita.notifier.port.adapter.dao.entities

import slick.jdbc.H2Profile.api._

import scala.concurrent.duration.{Duration, FiniteDuration}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 28.04.2019
  */
trait BaseTable {
  implicit val finiteDurationColumnType = MappedColumnType.base[FiniteDuration, String](
    fd => fd.toString, str => FiniteDuration(Duration(str)._1, Duration(str)._2))

}
