package ru.shaldnikita.users

import org.scalatest.DoNotDiscover
import org.scalatest.time.{Milliseconds, Seconds, Span}
import slick.jdbc.PostgresProfile

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
@DoNotDiscover
class DaoSpecBase extends ItSpecBase with PostgresProfile.API {

  lazy implicit val db: Database = TestContainerBasedDatabase.database

  override implicit val patienceConfig =
    PatienceConfig(Span(1, Seconds), Span(20, Milliseconds))
}
