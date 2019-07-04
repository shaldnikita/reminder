package port.adapter.dao.contact

import cats.data.NonEmptyList
import port.adapter.dao.Tables
import port.adapter.dao.Tables.contacts
import slick.jdbc.H2Profile.api._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactDao {
  def insert(contacts: NonEmptyList[ContactSchema]) = Tables.contacts ++= contacts.toList

  def update(contact: ContactSchema) = contacts.filter(_.contactId === contact.contactId)
    .update(contact)

  def updateUserContacts(userId: String, contacts: NonEmptyList[ContactSchema]) = {
    for {
      _ <- Tables.contacts.filter(_.userId === userId).delete
      _ <- Tables.contacts ++= contacts.toList
    } yield ()
  }

  def removeAllUserContacts(userId: String) = contacts.filter(_.userId === userId).delete

}
