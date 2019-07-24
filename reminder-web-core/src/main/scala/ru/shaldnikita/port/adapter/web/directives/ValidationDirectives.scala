package ru.shaldnikita.port.adapter.web.directives

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive1, Rejection, RejectionHandler}
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import cats.data.Validated.{Invalid, Valid}
import ru.shaldnikita.domain.validation.Validator._
import ru.shaldnikita.port.adapter.web.directives.ValidationDirectives.ValidationRejection
import ru.shaldnikita.port.adapter.web.models.validation.ValidationModels

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
trait ValidationDirectives {

  val ValidationRejectionHandler: RejectionHandler = RejectionHandler
    .newBuilder()
    .handle {
      case ValidationRejection(errors) =>
        complete(ValidationModels.validationError(errors))
    }
    .result()

  def validatedEntity[T](um: FromRequestUnmarshaller[T],
                         validate: T => ValidationResult[T]): Directive1[T] = {
    entity(um).flatMap { data =>
      validate(data) match {
        case Valid(validData) => provide(validData)
        case Invalid(errors)  => reject(ValidationRejection(errors))
      }
    }
  }
}

object ValidationDirectives extends ValidationDirectives {

  case class ValidationRejection(errors: ValidationErrors) extends Rejection

}
