package ru.shaldnikita.notifier.domain.entities

import java.util.UUID

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
case class User(email: String,
                firstName: String,
                secondName: String,
                phoneNumber: String,
                userId: String = UUID.randomUUID().toString)
