package ru.shaldnikita.port.adapter.web.models.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
trait DtoModel[M, D] extends DefaultJsonProtocol with SprayJsonSupport {
  def jsonFormat: RootJsonFormat[M]
  def toDomain(model: M): D
  def toModel(domain: D): M              = ???
  implicit val format: RootJsonFormat[M] = jsonFormat

}
