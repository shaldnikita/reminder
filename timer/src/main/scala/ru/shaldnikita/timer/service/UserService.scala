package ru.shaldnikita.timer.service

import java.util.UUID

import ru.shaldnikita.timer.dao.entities.User

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 03.04.2019
  */
class UserService {
  def find(userId: String)= {
    User(UUID.randomUUID().toString, "shaldnikita2@yandex.ru")
  }
}
