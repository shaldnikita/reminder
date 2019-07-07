package ru.shaldnikita.port.adapter.web.validation

import ru.shaldnikita.domain.validation.Validator
import ru.shaldnikita.domain.validation.Validator.ValidationResult
import ru.shaldnikita.port.adapter.web.models.dto.contacts.ContactModel

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
object ContactModelValidator extends Validator[ContactModel] {
  override def validate(model: ContactModel): ValidationResult[ContactModel] =
    ???
}
