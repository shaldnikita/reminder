package ru.shaldnikita.users.users.application.models.users

import ru.shaldnikita.users.users.domain.models.contacts.Contact

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
case class CreateUser(firstName: String,
                      secondName: String,
                      contacts: List[Contact])
