package ru.shaldnikita.users.port.adapter.web.models

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 08.07.2019
  */
trait BaseModel[T] extends WebModel[T] with ValidatedModel[T]
