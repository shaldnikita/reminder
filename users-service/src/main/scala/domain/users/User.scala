package domain.users

import java.util.UUID

import domain.users.contacts.{Contact, ContactType}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
final case class User(userId: String = UUID.randomUUID().toString,
                      firstName: String,
                      secondName: String,
                      contacts: Seq[Contact]) {
  def contactTypes: Set[ContactType] = contacts.map(_.`type`).toSet
}


