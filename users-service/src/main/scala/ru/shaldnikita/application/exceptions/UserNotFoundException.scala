package ru.shaldnikita.application.exceptions

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
final case class UserNotFoundException(userId: String)
    extends RuntimeException(s"User with id $userId not found.")
