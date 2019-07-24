package ru.shaldnikita.port.adapter.web.handlers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directive0, ExceptionHandler, Route}
import ru.shaldnikita.application.exceptions.{
  ContactNotFoundException,
  UserNotFoundException
}
import ru.shaldnikita.domain.exceptions.UsersServiceException
import ru.shaldnikita.port.adapter.web.directives.{
  BaseDirectives,
  ValidationDirectives
}
import ru.shaldnikita.port.adapter.web.protocols.BaseProtocols

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
trait BaseHandler extends BaseDirectives with BaseProtocols {

  def route: Route = withExceptionsHandling(withRejectionsHandling(routes))

  def withExceptionsHandling: Directive0 =
    handleExceptions(ExceptionHandler {
      case e: UserNotFoundException =>
        complete(StatusCodes.NotFound -> e.getMessage)
      case e: ContactNotFoundException =>
        complete(StatusCodes.NotFound -> e.getMessage)
      case e: UsersServiceException =>
        complete(StatusCodes.InternalServerError -> e.getMessage)
    })

  def withRejectionsHandling: Directive0 =
    handleRejections(ValidationDirectives.ValidationRejectionHandler)

  protected def routes: Route

}
