package ru.shaldnikita.domain.validation

import ru.shaldnikita.domain.validation.Validator.ValidationResult

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 08.07.2019
  */
class EmptyValidator[T] extends Validator[T] {
  override def validate(model: T): ValidationResult[T] = model.validNel
}
