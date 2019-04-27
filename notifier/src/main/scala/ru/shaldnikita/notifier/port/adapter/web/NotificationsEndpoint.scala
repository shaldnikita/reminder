package ru.shaldnikita.notifier.port.adapter.web

import java.util.UUID

import akka.actor.ActorRef
import akka.http.scaladsl.marshalling.Marshaller._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{path, _}
import akka.http.scaladsl.server.PathMatchers.JavaUUID
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import ru.shaldnikita.notifier.domain.entities.Notification
import ru.shaldnikita.notifier.domain.messages._

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class NotificationsEndpoint(notificationsManager: ActorRef, // provides access to notifications
                            notifyManager: ActorRef // provides access to  notifiers
                           )(implicit timeout: Timeout,
                             ec: ExecutionContext) {
  def route: Route = path("users" / JavaUUID) { userId =>
    path("notify" / JavaUUID) { notificationId =>
      get {
        complete {
          notifyManager ! NotifyNowMsg(userId.toString, notificationId.toString)
          StatusCodes.OK
        }
      }
    }

    path("notifications") {
      //create new notification
      (post & entity(as[models.Notification])) { notification =>
        complete {
          notificationsManager ? fromModel(notification)
          StatusCodes.OK
        }
      }

      path(JavaUUID) { notificationId =>
        //get single notification
        get {
          complete(
            (notificationsManager ? GetNotification(userId.toString, notificationId.toString))
              .mapTo[Notification]
              .map(toModel)
          )
        }
        (put & entity(as[models.Notification])) { notification =>
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
    models.Notification(n.text, n.notifyIn, n.isWholeDay, n.isRepeatable, n.repeatIn, Some(n.notificationId))
  }

  private def fromModel(n: models.Notification) = {
    NotificationMsg(
      text = n.text,
      notifyIn = n.notifyIn,
      isWholeDay = n.isWholeDay,
      isRepeatable = n.isRepeatable,
      repeatIn = n.repeatIn,
      id = n.id.map(UUID.fromString)
    )
  }
}
