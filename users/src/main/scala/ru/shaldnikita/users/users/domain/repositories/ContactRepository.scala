package ru.shaldnikita.users.users.domain.repositories

import cats.data.NonEmptyList
import ru.shaldnikita.users.users.domain.exceptions.RepositoryFailure
import ru.shaldnikita.users.users.domain.models.contacts.Contact
import ru.shaldnikita.users.users.domain.models.contacts.Contact.ContactId
import ru.shaldnikita.users.users.domain.models.users.User.UserId
import zio.IO

trait ContactRepository {
  def contactRepository: ContactRepository.Service
}

object ContactRepository {
  trait Service {
    def create(contact: Contact): IO[RepositoryFailure, Option[Int]]
    def create(
        contacts: NonEmptyList[Contact]): IO[RepositoryFailure, Option[Int]]
    def update(contact: Contact): IO[RepositoryFailure, Int]
    def delete(contactId: ContactId): IO[RepositoryFailure, Int]
    def findUserContacts(userId: UserId): IO[RepositoryFailure, Seq[Contact]]
    def updateAllUserContacts(
        userId: UserId,
        contacts: NonEmptyList[Contact]): IO[RepositoryFailure, Int]
    def removeAllUserContacts(userId: UserId): IO[RepositoryFailure, Int]
  }
}
