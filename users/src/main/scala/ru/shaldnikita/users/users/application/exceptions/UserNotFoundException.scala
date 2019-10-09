package ru.shaldnikita.users.users.application.exceptions

import ru.shaldnikita.users.users.domain.models.users.User.UserId

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
final case class UserNotFoundException(userId: UserId)
    extends RuntimeException(s"User with id ${userId.id} not found.")
