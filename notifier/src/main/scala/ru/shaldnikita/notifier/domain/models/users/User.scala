package ru.shaldnikita.notifier.domain.models.users

import java.util.UUID

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
case class User(userId: UserId,
                firstName: String,
                secondName: String,
                contacts: Seq[UserContact])

case class UserId(id: String = UUID.randomUUID().toString)

