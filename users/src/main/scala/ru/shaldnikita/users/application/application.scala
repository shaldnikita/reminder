package ru.shaldnikita.users

import ru.shaldnikita.users.domain.models.contacts.Contact
import ru.shaldnikita.users.domain.models.users.User
import ru.shaldnikita.users.port.adapter.dao.contact.ContactSchema
import ru.shaldnikita.users.port.adapter.dao.user.UserSchema
import ru.shaldnikita.users.port.adapter.dao.user.UserSchema
import ru.shaldnikita.users.domain.models.contacts.Contact
import ru.shaldnikita.users.domain.models.users.User
import ru.shaldnikita.users.port.adapter.dao.contact.ContactSchema
import ru.shaldnikita.users.port.adapter.dao.user.UserSchema
import ru.shaldnikita.users.port.adapter.dao.contact.ContactSchema
import ru.shaldnikita.users.port.adapter.dao.user.UserSchema
import ru.shaldnikita.users.domain.models.contacts.Contact
import ru.shaldnikita.users.domain.models.users.User

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
package object application {

  implicit class RichUser(val user: User) extends AnyVal {
    def toSchema: UserSchema =
      UserSchema(user.userId, user.firstName, user.secondName)
  }

  implicit class RichContact(val contact: Contact) extends AnyVal {
    def toSchema: ContactSchema =
      ContactSchema(
        contact.contactId,
        contact.`type`.id,
        contact.value,
        contact.userId
      )
  }
}
