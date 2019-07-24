package ru.shaldnikita.domain.exceptions

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
final case class UsersServiceException(message: String)
    extends RuntimeException(message)
