package ru.shaldnikita.users.port.adapter.web.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 08.07.2019
  */
trait WebModel[M] extends DefaultJsonProtocol with SprayJsonSupport {
  def jsonFormat: RootJsonFormat[M]
  implicit val format: RootJsonFormat[M] = jsonFormat
}
