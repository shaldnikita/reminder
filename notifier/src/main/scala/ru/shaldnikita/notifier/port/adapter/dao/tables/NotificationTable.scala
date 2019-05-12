package ru.shaldnikita.notifier.port.adapter.dao.tables

/*import ru.shaldnikita.notifier.domain.models.Notification
import ru.shaldnikita.notifier.port.adapter.dao.Tables
import slick.jdbc.H2Profile.api._

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 28.04.2019
  */
class NotificationTable(tag: Tag) extends Table[Notification](tag, "notifications") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  override def * = (notificationId,
    text, notifyIn, isWholeDay, isRepeatable, repeatIn, userId) <> (Notification.tupled, Notification.unapply)

  def notificationId = column[String]("notification_id")

  def text = column[String]("text")

  def notifyIn = column[FiniteDuration]("notify_in")

  def isWholeDay = column[Boolean]("is_whole_day")

  def isRepeatable = column[Boolean]("is_repeatable")

  def repeatIn = column[Option[FiniteDuration]]("repeate_in")

  def notification_id_udx = index("notifications_id_udx", userId, unique = true)

  def userId = column[String]("user_id")

  def user_id_fk = foreignKey("user_id", userId, Tables.users)

}*/
