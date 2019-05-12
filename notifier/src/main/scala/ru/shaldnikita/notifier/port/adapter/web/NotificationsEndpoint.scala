package ru.shaldnikita.notifier.port.adapter.web

import java.util.UUID

import akka.http.scaladsl.marshalling.Marshaller._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{path, _}
import akka.http.scaladsl.server.PathMatchers.JavaUUID
import akka.http.scaladsl.server.Route
import ru.shaldnikita.notifier.application.NotifyManager
import ru.shaldnikita.notifier.port.adapter.dao.repositories.NotificationRepository
import ru.shaldnikita.notifier.port.adapter.web.models.Notification

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class NotificationsEndpoint(notifyManager: NotifyManager,
                            notificationRepository: NotificationRepository)(
                             implicit ec: ExecutionContext) {
  def route: Route = path("users" / JavaUUID) { userId =>
    path("notify" / JavaUUID) { notificationId =>
      get {
        complete {
          notifyManager.notifyUser(userId.toString, notificationId.toString)
          StatusCodes.OK
        }
      }
    }

    path("notifications") {
      //create new notification
      /*(post & entity(as[models.Notification])) { notification =>
        complete {
          notificationsManager ? fromModel(notification)
          StatusCodes.OK
        }
      }*/

      path(JavaUUID) { notificationId =>
        //get single notification
        get {
          complete(
            notificationRepository.findByUserIdAndNotificationId(userId.toString, notificationId.toString)
              .map(_.map(toModel))
          )
        }
        (put & entity(as[Notification])) { notification =>
          complete {
            //todo add body
            notificationsManager ! fromModel(notification)
            StatusCodes.OK
          }
        }
      }

      get {
        //get all notifications
        pathEndOrSingleSlash {
          complete(
            (notificationsManager ? GetNotifications(userId.toString))
              .mapTo[List[Notification]]
              .map(notifications => notifications.map(toModel))
          )
        }
      }
    }


  }

  private def toModel(n: Notification) = {
    Notification(n.text, n.notifyIn, n.isWholeDay, n.isRepeatable, n.repeatIn, Some(n.notificationId))
  }

  private def fromModel(n: Notification) = {
    NotificationData(
      text = n.text,
      notifyIn = n.notifyIn,
      isWholeDay = n.isWholeDay,
      isRepeatable = n.isRepeatable,
      repeatIn = n.repeatIn,
      id = n.id.map(UUID.fromString)
    )
  }
}
