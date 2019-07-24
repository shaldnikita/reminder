package ru.shaldnikita

import ru.shaldnikita.domain.models.contacts.Contact
import ru.shaldnikita.domain.models.users.User
import ru.shaldnikita.port.adapter.dao.contact.ContactSchema
import ru.shaldnikita.port.adapter.dao.user.UserSchema

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
