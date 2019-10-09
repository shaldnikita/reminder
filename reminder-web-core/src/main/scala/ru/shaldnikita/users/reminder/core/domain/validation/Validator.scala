package ru.shaldnikita.users.reminder.core.domain.validation

import java.util.UUID

import cats.data.{NonEmptyList, ValidatedNel}
import cats.syntax.{ApplySyntax, ValidatedSyntax}
import Validator._

import scala.util.Try

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
trait Validator[T] extends ValidatedSyntax with ApplySyntax {

  def validate(model: T): ValidationResult[T]

  def notEmpty(source: String, field: String): ValidationResult[String] = {
    if (source.isEmpty)
      (field, "{must_not_be_empty}").invalidNel
    else
      source.validNel
  }

  def uuid(s: String, field: String): ValidationResult[String] = {
    Try(UUID.fromString(s))
      .map(_.toString.validNel)
      .getOrElse((field, "{not_uuid}").invalidNel)
  }
}

object Validator {
  type ValidationError     = (String, String)
  type ValidationErrors    = NonEmptyList[ValidationError]
  type ValidationResult[T] = ValidatedNel[ValidationError, T]

  protected class EmptyValidator[T] extends Validator[T] {
    override def validate(model: T): ValidationResult[T] = model.validNel
  }

  def empty[T] = new EmptyValidator[T]
}
