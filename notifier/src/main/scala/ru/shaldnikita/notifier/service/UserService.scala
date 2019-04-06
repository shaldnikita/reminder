package ru.shaldnikita.notifier.service

import ru.shaldnikita.notifier.dao.repositories.UserRepository

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
class UserService(userRepository: UserRepository) {

  def find(userId: String) = {
    userRepository.find(userId)
  }
}
