package ru.shaldnikita.users.users.application.exceptions

import ru.shaldnikita.users.users.domain.models.contacts.Contact.ContactId

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
final case class ContactNotFoundException(contactId: ContactId)
    extends RuntimeException(s"Contact with id ${contactId.id} not found.")
