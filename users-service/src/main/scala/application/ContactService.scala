package application

import application.Application._
import cats.data.NonEmptyList
import domain.users.contacts.Contact
import port.adapter.dao.Tables.contacts
import port.adapter.dao.contact.ContactDao
import slick.jdbc.H2Profile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactService(contactDao: ContactDao,
                     db: Database) {

  def insert(contacts: NonEmptyList[Contact]) =
    db.run(contactDao.insert(contacts.map(_.toSchema)))

  def removeContact(contactId: String) = db.run(contacts.filter(_.contactId === contactId).delete)

  def removeAllUserContacts(userId: String) = db.run(contactDao.removeAllUserContacts(userId))

  def update(contact: Contact) = db.run(contactDao.update(contact.toSchema))
}
