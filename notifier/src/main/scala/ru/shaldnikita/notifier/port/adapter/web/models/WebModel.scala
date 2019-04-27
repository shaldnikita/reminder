package ru.shaldnikita.notifier.port.adapter.web.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsNumber, JsObject, JsString, JsValue, RootJsonFormat, _}

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
trait WebModel[T] extends DefaultJsonProtocol with SprayJsonSupport {
  def jsonFormat: RootJsonFormat[T]
  implicit val format: RootJsonFormat[T] = jsonFormat

  implicit object FiniteDurationJsonFormat extends RootJsonFormat[FiniteDuration] {
    def write(dur: FiniteDuration) = JsObject(
      "length" -> JsNumber(dur.length),
      "unit" -> JsString(dur.unit.toString)
    )

    def read(value: JsValue) = {
      value.asJsObject.getFields("length", "unit") match {
        case Seq(JsNumber(length), JsString(unit)) => FiniteDuration(length.toLong, unit.toLowerCase)
        case _ => deserializationError("FiniteDuration expected")
      }
    }
  }

}
