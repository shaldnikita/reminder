package ru.shaldnikita.users.users.domain.repositories

import ru.shaldnikita.users.users.domain.exceptions.RepositoryFailure
import ru.shaldnikita.users.users.domain.models.users.User
import ru.shaldnikita.users.users.domain.models.users.User.UserId
import zio.IO

trait UserRepository {
  def userRepository: UserRepository.Service
}

object UserRepository {
  trait Service {
    def create(user: User): IO[RepositoryFailure, Int]

    def findOne(userId: UserId): IO[RepositoryFailure, Option[User]]

    def findAll(): IO[RepositoryFailure, Seq[User]]

    def update(user: User): IO[RepositoryFailure, Int]

    def deleteUserAndContacts(userId: UserId): IO[RepositoryFailure, Int]
  }
}
