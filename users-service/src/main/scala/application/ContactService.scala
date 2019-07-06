package application

import cats.data.NonEmptyList
import slick.jdbc.H2Profile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactService(
    contactDao: ContactDao,
    db: Database) {

  def insert(
      contacts: NonEmptyList[Contact]) =
    db.run(
      contactDao.insert(
        contacts.map(_.toSchema)))

  def removeContact(contactId: String) =
    db.run(contactDao.remove(contactId))

  def removeAllUserContacts(
      userId: String) =
    db.run(
      contactDao.removeAllUserContacts(
        userId))

  def update(contact: Contact) =
    db.run(
      contactDao.update(
        contact.toSchema))
}
