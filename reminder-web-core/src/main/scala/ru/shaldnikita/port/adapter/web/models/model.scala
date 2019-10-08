package ru.shaldnikita.users.port.adapter.web

import ru.shaldnikita.users.domain.validation.Validator.ValidationResult
import ru.shaldnikita.users.port.adapter.web.models.ValidatedModel

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 09.07.2019
  */
package object model {

  implicit class RichValidatedModel[T <: ValidatedModel[T]](val model: T)
      extends AnyVal {
    def validated: ValidationResult[_] = {
      model.validator.validate(model)
    }
  }

}
