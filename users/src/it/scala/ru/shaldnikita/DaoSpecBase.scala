package ru.shaldnikita

import org.scalatest.time.{Milliseconds, Seconds, Span}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, DoNotDiscover, OneInstancePerTest}
import slick.jdbc.PostgresProfile

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
@DoNotDiscover
class DaoSpecBase
  extends ItSpecBase
    with BeforeAndAfterAll
    with BeforeAndAfter
    with PostgresProfile.API {

  lazy implicit val db: Database = TestContainerBasedDatabase.database

  override implicit val patienceConfig =
    PatienceConfig(Span(1, Seconds), Span(20, Milliseconds))
}
