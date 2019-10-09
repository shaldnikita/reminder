package ru.shaldnikita.users.users.port.adapter.dao.contact

import cats.data.NonEmptyList
import ru.shaldnikita.users.DBTask
import ru.shaldnikita.users.users.port.adapter.dao.Tables.contacts
import ContactDaoImpl._
import ru.shaldnikita.users.port.adapter.dao._
import ru.shaldnikita.users.users.port.adapter.dao.Tables
import slick.jdbc.PostgresProfile.api._
import zio.ZIO

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactDaoImpl extends ContactDao {
  def create(contact: ContactSchema): DBTask[Option[Int]] = {
    ZIO.accessM[Database](_.runZ(CreateContacts(NonEmptyList.one(contact))))
  }

  def create(contacts: NonEmptyList[ContactSchema]): DBTask[Option[Int]] =
    ZIO.accessM[Database](_.runZ(CreateContacts(contacts)))

  def update(contact: ContactSchema): DBTask[Int] =
    ZIO.accessM[Database](_.runZ(UpdateContact(contact)))

  def delete(contactId: String): DBTask[Int] =
    ZIO.accessM[Database](_.runZ(DeleteContact(contactId)))

  def findUserContacts(userId: String): DBTask[Seq[ContactSchema]] =
    ZIO.accessM[Database](_.runZ(FindUserContacts(userId)))

  def updateAllUserContacts(
      userId: String,
      contacts: NonEmptyList[ContactSchema]): DBTask[Int] = {
    for {
      db <- ZIO.environment[Database]
      result <- ZIO.fromFuture { implicit rc =>
        val actions =
          for {
            removed <- RemoveAllUserContacts(userId)
            _       <- CreateContacts(contacts)
          } yield removed
        db.run(actions.transactionally)
      }
    } yield result
  }

  def removeAllUserContacts(userId: String): DBTask[Int] =
    for {
      db     <- ZIO.environment[Database]
      result <- db.runZ(RemoveAllUserContacts(userId))
    } yield result
}

object ContactDaoImpl {

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
