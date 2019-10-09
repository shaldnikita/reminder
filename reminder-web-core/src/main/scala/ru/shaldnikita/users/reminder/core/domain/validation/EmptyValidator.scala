package ru.shaldnikita.users.reminder.core.domain.validation

import Validator.ValidationResult

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 08.07.2019
  */
class EmptyValidator[T] extends Validator[T] {
  override def validate(model: T): ValidationResult[T] = model.validNel
}
