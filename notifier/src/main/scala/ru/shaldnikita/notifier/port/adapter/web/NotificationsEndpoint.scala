package ru.shaldnikita.notifier.port.adapter.web

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
import ru.shaldnikita.notifier.port.adapter.web.models.NewNotification

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
        complete(
          (notifyManager ? NotifyNowMessage(userId.toString, notificationId.toString))
            .mapTo[models.Notification]
        )
      }
    }


    path("notifications") {
      //create new notification
      (post & entity(as[NewNotification])) { notification =>
        complete {
          notificationsManager ? CreateNotification
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
        (put & entity(as[NewNotification])) { notification =>
          complete {
            //todo add body
            notificationsManager ! UpdateNotification()
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
    models.Notification(n.id, n.text, n.notifyIn, n.isWholeDay, n.isRepeatable, n.repeatIn)
  }
}
