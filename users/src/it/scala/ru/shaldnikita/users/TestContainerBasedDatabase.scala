package ru.shaldnikita.users

import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.testcontainers.containers.PostgreSQLContainer
import ru.shaldnikita.users.port.adapter.dao.Tables
import ru.shaldnikita.users.users.port.adapter.dao.Tables
import slick.jdbc.PostgresProfile

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 23.07.2019
  */
object TestContainerBasedDatabase
    extends PostgresProfile.API
    with ScalaFutures {

  /**
    * Will be shut down automatically when JVM process will be stopped
    */
  lazy val container: PostgreSQLContainer[Nothing] = {
    val c = new PostgreSQLContainer("postgres:12")
    c.start()
    while (!c.isCreated) {}
    c
  }

  lazy val database = {
    val db = Database.forURL(
      TestContainerBasedDatabase.container.getJdbcUrl,
      TestContainerBasedDatabase.container.getUsername,
      TestContainerBasedDatabase.container.getPassword,
      keepAliveConnection = true
    )
    db.run(Tables.setup).futureValue(Timeout(Span(3, Seconds)))
    db
  }

}
