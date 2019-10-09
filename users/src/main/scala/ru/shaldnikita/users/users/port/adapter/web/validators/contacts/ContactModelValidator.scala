package ru.shaldnikita.users.users.port.adapter.web.validators.contacts

import ru.shaldnikita.reminder.core.domain.validation.Validator
import ru.shaldnikita.reminder.core.domain.validation.Validator.ValidationResult
import ru.shaldnikita.users.port.adapter.web.models.contacts.ContactModel

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
object ContactModelValidator extends Validator[ContactModel] {
  override def validate(model: ContactModel): ValidationResult[ContactModel] =
    (
      uuid(model.contactId, "contact_id"),
      notEmpty(model.`type`, "type"),
      notEmpty(model.value, "value"),
      uuid(model.userId, "user_id")
    ).mapN(ContactModel.apply)
}
