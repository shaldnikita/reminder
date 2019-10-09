package ru.shaldnikita.users.users.port.adapter.web.handlers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directive0, ExceptionHandler, Route}
import ru.shaldnikita.port.adapter.web.directives.{BaseDirectives, ValidationDirectives}
import ru.shaldnikita.port.adapter.web.protocols.BaseProtocols
import ru.shaldnikita.users.application.exceptions.{ContactNotFoundException, UserNotFoundException}
import ru.shaldnikita.users.domain.exceptions.UsersServiceException
import ru.shaldnikita.users.port.adapter.web.directives.{BaseDirectives, ValidationDirectives}
import ru.shaldnikita.users.port.adapter.web.marshallers.ZIOMarshallers
import ru.shaldnikita.users.port.adapter.web.protocols.BaseProtocols
import ru.shaldnikita.users.users.application.exceptions.{ContactNotFoundException, UserNotFoundException}
import ru.shaldnikita.users.users.domain.exceptions.UsersException
import ru.shaldnikita.users.users.port.adapter.web.marshallers.ZIOMarshallers

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
trait BaseHandler
    extends BaseDirectives
    with BaseProtocols
    with ZIOMarshallers {

  def route: Route = withExceptionsHandling(withRejectionsHandling(routes))

  def withExceptionsHandling: Directive0 =
    handleExceptions(ExceptionHandler {
      case e: UserNotFoundException =>
        complete(StatusCodes.NotFound -> e.getMessage)
      case e: ContactNotFoundException =>
        complete(StatusCodes.NotFound -> e.getMessage)
      case e: UsersException =>
        complete(StatusCodes.InternalServerError -> e.getMessage)
    })

  def withRejectionsHandling: Directive0 =
    handleRejections(ValidationDirectives.ValidationRejectionHandler)

  protected def routes: Route

}
