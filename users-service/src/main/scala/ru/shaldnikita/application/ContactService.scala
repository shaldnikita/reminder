package ru.shaldnikita.application

import cats.data.NonEmptyList
import ru.shaldnikita.application.Application._
import ru.shaldnikita.domain.users.contacts.Contact
import ru.shaldnikita.port.adapter.dao.contact.ContactDao
import slick.jdbc.H2Profile.api._

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactService(contactDao: ContactDao)(implicit db: Database,
                                             ec: ExecutionContext) {
  def create(contact: Contact): Future[Contact] =
    contactDao
      .create(contact.toSchema)
      .map(_ => contact)

  def create(contacts: NonEmptyList[Contact]): Future[NonEmptyList[Contact]] =
    contactDao
      .create(contacts.map(_.toSchema))
      .map(_ => contacts)

  def update(contact: Contact): Future[Option[Contact]] =
    contactDao
      .update(contact.toSchema)
      .map {
        case 1 => Some(contact)
        case 0 => None
      }

  def delete(contactId: String) =
    contactDao.delete(contactId).map {
      case 1 => Some(())
      case 0 => None
    }

  def findUserContacts(userId: String): Future[Seq[Contact]] =
    contactDao
      .findUserContacts(userId)
      .map(_.map(_.toDomain))

  def updateUserContacts(
      userId: String,
      contacts: NonEmptyList[Contact]): Future[Option[Unit]] =
    contactDao
      .updateAllUserContacts(userId, contacts.map(_.toSchema))
      .map {
        case 1 => Some(())
        case 0 => None
      }

  def removeAllUserContacts(userId: String): Future[Int] =
    contactDao.removeAllUserContacts(userId)
}
