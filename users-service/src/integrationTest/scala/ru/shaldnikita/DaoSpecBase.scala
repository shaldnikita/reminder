package ru.shaldnikita

import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.testcontainers.containers.PostgreSQLContainer
import ru.shaldnikita.port.adapter.dao.Tables
import slick.jdbc.PostgresProfile

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
class DaoSpecBase
    extends SpecBase
    with BeforeAndAfterAll
    with BeforeAndAfter
    with PostgresProfile.API {

  val container: PostgreSQLContainer[Nothing] = {
    val c = new PostgreSQLContainer("postgres:12")
    c.start()
    c
  }

  lazy implicit val db: Database = database

  def database = {
    val db = Database.forURL(
      container.getJdbcUrl,
      container.getUsername,
      container.getPassword,
      keepAliveConnection = true
    )
    db.run(Tables.setup).futureValue(Timeout(Span(3, Seconds)))
    db
  }

  override protected def afterAll() = container.stop()
}
