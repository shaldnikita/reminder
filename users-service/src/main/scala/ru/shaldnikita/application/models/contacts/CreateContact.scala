package ru.shaldnikita.application.models.contacts

import ru.shaldnikita.domain.models.contacts.ContactType

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
case class CreateContact(`type`: ContactType, value: String, userId: String)
