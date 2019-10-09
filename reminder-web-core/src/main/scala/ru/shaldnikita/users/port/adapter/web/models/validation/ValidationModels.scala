package ru.shaldnikita.users.port.adapter.web.models.validation

import ru.shaldnikita.users.reminder.core.domain.validation.Validator.ValidationErrors
import spray.json.DefaultJsonProtocol._
import spray.json.{JsValue, _}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
object ValidationModels {

  type Response     = (Int, JsValue)
  type ErrorDetails = Option[Map[String, String]]

  def validationError(errors: ValidationErrors): Response =
    (400,
     ApiResponseError(
       400,
       ErrorDto(details = ValidationHelper.convertErrors(errors)))
       .toJson(ApiResponseError.format))

  case class ErrorDto(
      message: Option[String] = None,
      details: ErrorDetails = None
  )

  case class ApiResponseError(status: Int, error: ErrorDto)

  object ErrorDto {
    implicit val format = jsonFormat2(ErrorDto.apply)
  }

  object ApiResponseError {
    implicit val format = jsonFormat2(ApiResponseError.apply)
  }

  object ValidationHelper {

    def convertErrors(errors: ValidationErrors): ErrorDetails = {
      Some(errors.tail.toMap + errors.head)
    }

  }
}
