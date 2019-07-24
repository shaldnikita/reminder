package ru.shaldnikita.port.adapter.web.validators.users

import cats.implicits._
import ru.shaldnikita.domain.validation.Validator
import ru.shaldnikita.domain.validation.Validator.ValidationResult
import ru.shaldnikita.port.adapter.web.models.users.CreateUserModel
import ru.shaldnikita.port.adapter.web.validators.contacts.ContactModelValidator

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
object CreateUserModelValidator extends Validator[CreateUserModel] {

  override def validate(
      user: CreateUserModel
  ): ValidationResult[CreateUserModel] = {
    (
      notEmpty(user.firstName, "firstName"),
      notEmpty(user.secondName, "secondName"),
      user.contacts.map(ContactModelValidator.validate).sequence
    ).mapN(CreateUserModel.apply)
  }
}
