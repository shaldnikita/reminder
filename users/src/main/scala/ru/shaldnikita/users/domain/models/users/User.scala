package ru.shaldnikita.users.domain.models.users

import ru.shaldnikita.users.domain.models.contacts.ContactType
import ru.shaldnikita.users.domain.models.contacts.{Contact, ContactType}
import ru.shaldnikita.users.domain.models.contacts.ContactType

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
final case class User(userId: String,
                      firstName: String,
                      secondName: String,
                      contacts: List[Contact]) {
  def contactTypes: Set[ContactType] = contacts.map(_.`type`).toSet
}
