package application

import domain.users.User
import domain.users.contacts.Contact
import port.adapter.dao.contact.ContactSchema
import port.adapter.dao.user.UserSchema

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */

object Application {
  implicit class RichUser(val user: User) extends AnyVal {
    def toSchema: UserSchema = UserSchema(user.userId, user.firstName, user.secondName)
  }

  implicit class RichContact(val contact: Contact) extends AnyVal {
    def toSchema: ContactSchema = ContactSchema(contact.contactId, contact.`type`.entryName, contact.value, contact.userId)
  }
}
