package ru.shaldnikita.users.users.application

import java.util.UUID

import cats.data.NonEmptyList
import ru.shaldnikita.users.application.exceptions.{ContactNotFoundException, UserNotFoundException}
import ru.shaldnikita.users.application.models.contacts.CreateContact
import ru.shaldnikita.users.domain.models.contacts.Contact
import ru.shaldnikita.users.port.adapter.dao.contact.ContactDaoImpl
import ru.shaldnikita.users.users.application.exceptions.{ContactNotFoundException, UserNotFoundException}
import ru.shaldnikita.users.users.application.models.contacts.CreateContact
import ru.shaldnikita.users.users.domain.models.contacts.Contact
import ru.shaldnikita.users.users.port.adapter.dao.contact.ContactDaoImpl
import slick.jdbc.PostgresProfile.api.Database
import zio.{Task, ZIO}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactService(contactDao: ContactDaoImpl, db: Database) {
  def create(contact: CreateContact): Task[Contact] = {
    import contact._
    val createdContact =
      Contact(UUID.randomUUID().toString, `type`, value, userId)
    contactDao
      .create(createdContact.toSchema)
      .provide(db)
      .map(_ => createdContact)
  }

  def create(
      contacts: NonEmptyList[CreateContact]): Task[NonEmptyList[Contact]] = {
    val createdContacts = contacts.map(fillWithId)
    contactDao
      .create(createdContacts.map(_.toSchema))
      .provide(db)
      .map(_ => createdContacts)
  }

  private def fillWithId(contact: CreateContact) = {
    import contact._
    Contact(UUID.randomUUID().toString, `type`, value, userId)
  }

  def update(contact: Contact): Task[Contact] =
    contactDao
      .update(contact.toSchema)
      .provide(db)
      .flatMap {
        case 1 => ZIO.succeed(contact)
        case 0 => ZIO.fail(ContactNotFoundException(contact.contactId))
      }

  def delete(contactId: String): Task[Unit] =
    contactDao.delete(contactId).provide(db).flatMap {
      case 1 => ZIO.succeed(())
      case 0 => ZIO.fail(ContactNotFoundException(contactId))
    }

  def findUserContacts(userId: String): Task[Seq[Contact]] =
    contactDao
      .findUserContacts(userId)
      .provide(db)
      .map(_.map(_.toDomain))

  def update(userId: String, contacts: NonEmptyList[Contact]): Task[Unit] =
    contactDao
      .updateAllUserContacts(userId, contacts.map(_.toSchema))
      .provide(db)
      .flatMap {
        case 1 => ZIO.succeed(())
        case 0 => ZIO.fail(UserNotFoundException(userId))
      }

  //TODO case if user not found
  def deleteAllUserContacts(userId: String): Task[Unit] =
    contactDao.removeAllUserContacts(userId).provide(db).map(_ => ())
}

object ContactService {}
