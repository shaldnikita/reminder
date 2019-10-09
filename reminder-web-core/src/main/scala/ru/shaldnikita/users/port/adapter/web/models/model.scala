package ru.shaldnikita.users.port.adapter.web

import ru.shaldnikita.users.reminder.core.domain.validation.Validator.ValidationResult

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 09.07.2019
  */
package object models {

  implicit class RichValidatedModel[T <: ValidatedModel[T]](val model: T)
      extends AnyVal {
    def validated: ValidationResult[_] = {
      model.validator.validate(model)
    }
  }

}
