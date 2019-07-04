package ru.shaldnikita.notifier.domain.models.users

import java.util.UUID

import ru.shaldnikita.notifier.domain.models.users.contacts.{Contact, ContactType}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
final case class User(userId: UserId,
                firstName: String,
                secondName: String,
                contacts: Seq[Contact]) {
  def contactTypes: Set[ContactType] = contacts.map(_.`type`).toSet
}

case class UserId(id: String = UUID.randomUUID().toString)

