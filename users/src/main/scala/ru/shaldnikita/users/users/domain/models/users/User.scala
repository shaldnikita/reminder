package ru.shaldnikita.users.users.domain.models.users

import java.util.UUID

import ru.shaldnikita.users.users.domain.models.contacts.{Contact, ContactType}
import ru.shaldnikita.users.users.domain.models.users.User.UserId

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
final case class User(userId: UserId,
                      firstName: String,
                      secondName: String,
                      contacts: List[Contact]) {
  def contactTypes: Set[ContactType] = contacts.map(_.`type`).toSet
}

object User {
  case class UserId(id: String) extends AnyVal

  object UserId {
    def apply(): UserId = new UserId(UUID.randomUUID().toString)

    def apply(id: String): UserId = new UserId(id)
  }

}
