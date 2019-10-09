package ru.shaldnikita.users.users.port.adapter.web.marshallers

import akka.http.scaladsl.marshalling.Marshaller
import akka.http.scaladsl.server.Route
import ru.shaldnikita.users.{BTask, users}

trait ZIOMarshallers {

  implicit def zio2Marshaller[A, B](
      implicit m1: Marshaller[A, B],
      m2: Marshaller[Throwable, B]): Marshaller[BTask[A], B] =
    Marshaller { implicit ec => a =>
      users.ZIORuntime.unsafeRun(a.fold(e => m2(e), a => m1(a)))
    }

  implicit def zioRouteMarshaller(zioRoute: BTask[Route]): Route =
    users.ZIORuntime.unsafeRun(zioRoute)
}
