package ru.shaldnikita.application.exceptions

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
final case class ContactNotFoundException(contactId: String)
    extends RuntimeException(s"Contact with id $contactId not found.")
