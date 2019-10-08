package ru.shaldnikita.users.port.adapter.web.validators.users

import cats.implicits._
import ru.shaldnikita.users.domain.validation.Validator
import ru.shaldnikita.users.domain.validation.Validator.ValidationResult
import ru.shaldnikita.users.port.adapter.web.models.users.UserModel
import ru.shaldnikita.users.port.adapter.web.validators.contacts.ContactModelValidator
import ru.shaldnikita.users.port.adapter.web.models.users.UserModel
import ru.shaldnikita.users.port.adapter.web.validators.contacts.ContactModelValidator
import ru.shaldnikita.users.port.adapter.web.models.users.UserModel
import ru.shaldnikita.users.port.adapter.web.validators.contacts.ContactModelValidator
import ru.shaldnikita.users.port.adapter.web.models.users.UserModel
import ru.shaldnikita.users.port.adapter.web.validators.contacts.ContactModelValidator

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
object UserModelValidator extends Validator[UserModel] {

  override def validate(user: UserModel): ValidationResult[UserModel] = {
    (
      uuid(user.userId, "user_id"),
      notEmpty(user.firstName, "firstName"),
      notEmpty(user.secondName, "secondName"),
      user.contacts.map(ContactModelValidator.validate).sequence
    ).mapN(UserModel.apply)
  }
}
