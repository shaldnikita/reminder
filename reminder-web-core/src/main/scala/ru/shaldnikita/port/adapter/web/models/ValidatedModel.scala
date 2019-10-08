package ru.shaldnikita.users.port.adapter.web.models

import ru.shaldnikita.users.domain.validation.Validator

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 08.07.2019
  */
trait ValidatedModel[T] {

  def validator: Validator[T] = Validator.empty

}
