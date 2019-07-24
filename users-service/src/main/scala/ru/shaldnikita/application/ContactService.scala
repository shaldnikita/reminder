package ru.shaldnikita.application

import java.util.UUID

import cats.data.NonEmptyList
import ru.shaldnikita.application.exceptions.{
  ContactNotFoundException,
  UserNotFoundException
}
import ru.shaldnikita.application.models.contacts.CreateContact
import ru.shaldnikita.domain.models.contacts.Contact
import ru.shaldnikita.port.adapter.dao.contact.ContactDao
import slick.jdbc.H2Profile.api._

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactService(contactDao: ContactDao)(implicit db: Database,
                                             ec: ExecutionContext) {
  def create(contact: CreateContact): Future[Contact] = {
    import contact._
    val createdContact =
      Contact(UUID.randomUUID().toString, `type`, value, userId)
    contactDao
      .create(createdContact.toSchema)
      .map(_ => createdContact)
  }

  def create(
      contacts: NonEmptyList[CreateContact]
  ): Future[NonEmptyList[Contact]] = {
    val createdContacts = contacts.map(fillWithId)
    contactDao
      .create(createdContacts.map(_.toSchema))
      .map(_ => createdContacts)
  }

  private def fillWithId(contact: CreateContact) = {
    import contact._
    Contact(UUID.randomUUID().toString, `type`, value, userId)
  }

  def update(contact: Contact): Future[Contact] =
    contactDao
      .update(contact.toSchema)
      .flatMap {
        case 1 => Future.successful(contact)
        case 0 => Future.failed(ContactNotFoundException(contact.contactId))
      }

  def delete(contactId: String): Future[Unit] =
    contactDao.delete(contactId).flatMap {
      case 1 => Future.successful(())
      case 0 => Future.failed(ContactNotFoundException(contactId))
    }

  def findUserContacts(userId: String): Future[Seq[Contact]] =
    contactDao
      .findUserContacts(userId)
      .map(_.map(_.toDomain))

  def update(userId: String, contacts: NonEmptyList[Contact]): Future[Unit] =
    contactDao
      .updateAllUserContacts(userId, contacts.map(_.toSchema))
      .flatMap {
        case 1 => Future.successful(())
        case 0 => Future.failed(UserNotFoundException(userId))
      }

  //TODO  0 contacts for user => wrong exception
  def deleteAllUserContacts(userId: String): Future[Unit] =
    contactDao.removeAllUserContacts(userId).flatMap {
      case 1 => Future.successful(())
      case 0 => Future.failed(UserNotFoundException(userId))
    }
}
