package ru.shaldnikita.application

import cats.data.NonEmptyList
import ru.shaldnikita.application.Application._
import ru.shaldnikita.domain.users.contacts.Contact
import ru.shaldnikita.port.adapter.dao.contact.ContactDao
import slick.jdbc.H2Profile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactService(contactDao: ContactDao)(implicit db: Database) {
  def create(contact: Contact) =
    contactDao.create(contact.toSchema)

  def create(contacts: NonEmptyList[Contact]) =
    contactDao.create(contacts.map(_.toSchema))

  def update(contact: Contact) =
    contactDao.update(contact.toSchema)

  def delete(contactId: String) =
    contactDao.delete(contactId)

  def findUserContacts(userId: String) =
    contactDao.findUserContacts(userId)

  def updateUserContacts(userId: String, contacts: NonEmptyList[Contact]) =
    contactDao.updateUserContacts(userId, contacts.map(_.toSchema))

  def removeAllUserContacts(userId: String) =
    contactDao.removeAllUserContacts(userId)
}
