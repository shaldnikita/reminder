package ru.shaldnikita.users.port.adapter.web.models

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
trait DtoModel[M, D] extends WebModel[M] with ValidatedModel[M] {
  def toDomain(model: M): D
}
