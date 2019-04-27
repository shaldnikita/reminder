package ru.shaldnikita.notifier.port.adapter.dao.repositories

import ru.shaldnikita.notifier.domain.entities.Notification
import ru.shaldnikita.notifier.port.adapter.dao.Tables
import ru.shaldnikita.notifier.port.adapter.dao.entities.NotificationTable
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 28.04.2019
  */
class NotificationRepository(db: Database) extends TableQuery(new NotificationTable(_)) {
  private val notifications = Tables.notifications

  def findByUserId(userId: String): Future[Seq[Notification]] = {
    db.run(notifications.filter(_.userId === userId).result)
  }

  def findById(notificationId: String): Future[Option[Notification]] = {
    db.run(notifications.filter(_.notificationId === notificationId).result.headOption)
  }
}
