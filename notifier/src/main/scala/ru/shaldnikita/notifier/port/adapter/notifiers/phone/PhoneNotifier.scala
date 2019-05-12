package ru.shaldnikita.notifier.port.adapter.notifiers.phone


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import ru.shaldnikita.notifier.domain.messages.{NotificationData, User}
import ru.shaldnikita.notifier.domain.notifiers.{Notifier, SmsNotification$}
import ru.shaldnikita.notifier.port.adapter.notifiers.phone.conf.PhoneConfiguration

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.04.2019
  */
class PhoneNotifier(conf: PhoneConfiguration)
                   (implicit actorSystem: ActorSystem,
                       ec: ExecutionContext) extends Notifier {

  override def notify(user: User, notification: NotificationData)
                     (implicit ec: ExecutionContext) = Future {
    val uri = s"https://sms.ru/sms/send?api_id=${conf.apiId}" +
      s"to=${user.phoneNumber}&msg=hello+world&json=1"
    Http().singleRequest(HttpRequest(method = HttpMethods.GET, uri))
  }

  override def notificationType = SmsNotification$
}
