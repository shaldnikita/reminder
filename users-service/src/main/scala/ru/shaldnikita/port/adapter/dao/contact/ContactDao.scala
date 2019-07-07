package ru.shaldnikita.port.adapter.dao.contact

import cats.data.NonEmptyList
import ru.shaldnikita.port.adapter.dao.Tables.contacts
import ContactDao._
import ru.shaldnikita.port.adapter.dao.Tables
import slick.jdbc.H2Profile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactDao {
  def create(contact: ContactSchema)(implicit db: Database) =
    db.run(CreateContacts(NonEmptyList.one(contact)))

  def create(contacts: NonEmptyList[ContactSchema])(implicit db: Database) =
    db.run(CreateContacts(contacts))

  def update(contact: ContactSchema)(implicit db: Database) =
    db.run(UpdateContact(contact))

  def delete(contactId: String)(implicit db: Database) =
    db.run(DeleteContact(contactId))

  def findUserContacts(userId: String)(implicit db: Database) =
    db.run(FindUserContacts(userId))

  def updateUserContacts(userId: String, contacts: NonEmptyList[ContactSchema])(
      implicit db: Database) =
    db.run(UpdateUserContacts(userId, contacts))

  def removeAllUserContacts(userId: String)(implicit db: Database) =
    db.run(RemoveAllUserContacts(userId))

}

object ContactDao {

  val CreateContacts = (contacts: NonEmptyList[ContactSchema]) =>
    Tables.contacts ++= contacts.toList

  val UpdateContact = (contact: ContactSchema) =>
    contacts
      .filter(_.contactId === contact.contactId)
      .update(contact)

  val DeleteContact = (contactId: String) =>
    contacts.filter(_.contactId === contactId).delete

  val FindUserContacts = (userId: String) =>
    contacts.filter(_.userId === userId).result

  val RemoveAllUserContacts = (userId: String) =>
    contacts.filter(_.userId === userId).delete

  val UpdateUserContacts =
    (userId: String, contacts: NonEmptyList[ContactSchema]) =>
      DBIO.seq(
        Tables.contacts.filter(_.userId === userId).delete,
        Tables.contacts ++= contacts.toList
    )

}
