package ru.shaldnikita.port.adapter.web.models

import ru.shaldnikita.domain.validation.Validator

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 08.07.2019
  */
trait ValidatedModel[T] {

  def validator: Validator[T] = Validator.empty

}
