package ru.shaldnikita.notifier.notificators.phone


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import ru.shaldnikita.notifier.dao.entities.User
import ru.shaldnikita.notifier.notificators.Notificator
import ru.shaldnikita.notifier.notificators.phone.conf.PhoneConfiguration

import scala.async.Async._
import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.04.2019
  */
class PhoneNotificator(implicit actorSystem: ActorSystem,
                       conf: PhoneConfiguration) extends Notificator {

  override def notify(user: User)(implicit ec: ExecutionContext) = async {
    val uri = s"https://sms.ru/sms/send?api_id=${conf.apiId}" +
      s"to=${user.phoneNumber}&msg=hello+world&json=1"
    Http().singleRequest(HttpRequest(method = HttpMethods.GET, uri))
  }
}
