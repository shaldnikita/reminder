package ru.shaldnikita.users.users.application.models.contacts

import ru.shaldnikita.users.users.domain.models.contacts.ContactType
import ru.shaldnikita.users.users.domain.models.users.User.UserId

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
case class CreateContact(`type`: ContactType, value: String, userId: UserId)
