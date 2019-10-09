package ru.shaldnikita.users.users.application

import java.util.UUID

import cats.data.NonEmptyList
import ru.shaldnikita.users.users.application.exceptions.{
  ContactNotFoundException,
  UserNotFoundException
}
import ru.shaldnikita.users.users.application.models.contacts.CreateContact
import ru.shaldnikita.users.users.domain.models.contacts.Contact
import ru.shaldnikita.users.users.domain.models.contacts.Contact.ContactId
import ru.shaldnikita.users.users.domain.models.users.User.UserId
import ru.shaldnikita.users.users.domain.repositories.{
  ContactRepository,
  UserRepository
}
import ru.shaldnikita.users.users.port.adapter.dao.contact.ContactDaoImpl
import slick.jdbc.PostgresProfile.api.Database
import zio.{RIO, ZIO}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactService(contactDao: ContactDaoImpl, db: Database) {

  def create(contact: CreateContact): RIO[ContactRepository, Contact] = {
    import contact._
    val createdContact =
      Contact(ContactId(UUID.randomUUID().toString), `type`, value, userId)
    ZIO
      .accessM[ContactRepository](_.contactRepository.create(createdContact))
      .map(_ => createdContact)
  }

  def create(contacts: NonEmptyList[CreateContact])
    : RIO[ContactRepository, NonEmptyList[Contact]] = {
    val createdContacts = contacts.map(fillWithId)
    ZIO
      .accessM[ContactRepository](_.contactRepository.create(createdContacts))
      .map(_ => createdContacts)
  }

  private def fillWithId(contact: CreateContact): Contact = {
    import contact._
    Contact(ContactId(UUID.randomUUID().toString), `type`, value, userId)
  }

  def update(contact: Contact): RIO[ContactRepository, Contact] =
    ZIO
      .accessM[ContactRepository](_.contactRepository.update(contact))
      .flatMap {
        case 1 => ZIO.succeed(contact)
        case 0 => ZIO.fail(ContactNotFoundException(contact.contactId))
      }

  def delete(contactId: ContactId): RIO[ContactRepository, Unit] =
    ZIO
      .accessM[ContactRepository](
        _.contactRepository
          .delete(contactId))
      .flatMap {
        case 1 => ZIO.succeed(())
        case 0 => ZIO.fail(ContactNotFoundException(contactId))
      }

  def findUserContacts(userId: UserId): RIO[ContactRepository, Seq[Contact]] =
    ZIO.accessM[ContactRepository](
      _.contactRepository.findUserContacts(userId)
    )

  def update(userId: UserId,
             contacts: NonEmptyList[Contact]): RIO[ContactRepository, Unit] =
    ZIO
      .accessM[ContactRepository](
        _.contactRepository
          .updateAllUserContacts(userId, contacts))
      .flatMap {
        case 1 => ZIO.succeed(())
        case 0 => ZIO.fail(UserNotFoundException(userId))
      }

  def deleteAllUserContacts(
      userId: UserId): RIO[ContactRepository with UserRepository, Unit] =
    for {
      _ <- ZIO
        .accessM[UserRepository](_.userRepository.findOne(userId))
        .someOrFail(UserNotFoundException(userId))
      _ <- ZIO
        .accessM[ContactRepository](
          _.contactRepository.removeAllUserContacts(userId))
        .map(_ => ())
    } yield ()

}
