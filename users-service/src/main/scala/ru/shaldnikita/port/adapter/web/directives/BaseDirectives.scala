package ru.shaldnikita.port.adapter.web.directives
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directive0, Directives, ExceptionHandler}
import ru.shaldnikita.application.exceptions.{
  ContactNotFoundException,
  UserNotFoundException
}
import ru.shaldnikita.domain.exceptions.UsersServiceException
import ru.shaldnikita.port.adapter.web.directives.validation.ValidationDirectives

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
trait BaseDirectives extends Directives with ValidationDirectives {
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
}
