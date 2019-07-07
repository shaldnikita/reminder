package ru.shaldnikita.domain.users

import ru.shaldnikita.domain.users.contacts.{Contact, ContactType}

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
