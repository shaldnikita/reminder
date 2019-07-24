package ru.shaldnikita.port.adapter.web.protocols

import akka.http.scaladsl.marshalling.PredefinedToResponseMarshallers._
import akka.http.scaladsl.marshalling.{
  ToEntityMarshaller,
  ToResponseMarshallable
}
import akka.http.scaladsl.model.{StatusCode, StatusCodes}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
trait BaseProtocols {
  //TODO can`t extend AnyValue
  implicit class RichOption[A](val value: Option[A]) {
    def toResponse(statusCode: StatusCode, messageIfMissing: String)(
        implicit marshaller0: ToEntityMarshaller[A]): ToResponseMarshallable = {
      value match {
        case Some(v) => statusCode           -> v
        case None    => StatusCodes.NotFound -> messageIfMissing
      }
    }
  }
}
