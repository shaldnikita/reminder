package ru.shaldnikita.port.adapter.web.validation

import cats.implicits._
import ru.shaldnikita.domain.validation.Validator
import ru.shaldnikita.domain.validation.Validator.ValidationResult
import ru.shaldnikita.port.adapter.web.models.dto.users.UserModel

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
