package ru.shaldnikita.notifier.domain.exceptions

final class UserNotFoundException(userId: String)
  extends RuntimeException(s"User $userId cannot be found.")
