package ru.shaldnikita.users.users.domain.exceptions

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
final case class UsersException(message: String)
    extends RuntimeException(message)
