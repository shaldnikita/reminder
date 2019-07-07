package ru.shaldnikita.port.adapter.web.validation

import ru.shaldnikita.domain.validation.Validator
import ru.shaldnikita.port.adapter.web.models.dto.contacts.CreateContactModel

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
object CreateContactModelValidator extends Validator[CreateContactModel] {
  override def validate(model: CreateContactModel) = ???
}
