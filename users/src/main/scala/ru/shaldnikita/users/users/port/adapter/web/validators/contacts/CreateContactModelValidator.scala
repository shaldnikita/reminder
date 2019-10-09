package ru.shaldnikita.users.users.port.adapter.web.validators.contacts

import ru.shaldnikita.reminder.core.domain.validation.Validator
import ru.shaldnikita.users.port.adapter.web.models.contacts.CreateContactModel

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
object CreateContactModelValidator extends Validator[CreateContactModel] {
  override def validate(model: CreateContactModel) =
    (
      notEmpty(model.`type`, "type"),
      notEmpty(model.value, "value"),
      uuid(model.userId, "user_id")
    ).mapN(CreateContactModel.apply)
}
