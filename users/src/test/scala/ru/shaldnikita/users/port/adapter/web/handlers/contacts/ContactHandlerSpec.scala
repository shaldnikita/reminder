package ru.shaldnikita.users.port.adapter.web.handlers.contacts

import ru.shaldnikita.users.HandlerSpecBase
import ru.shaldnikita.users.users.application.ContactService
import ru.shaldnikita.users.users.port.adapter.web.handlers.contacts.ContactHandler

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 18.07.2019
  */
class ContactHandlerSpec extends HandlerSpecBase {
  private val contactService = mock[ContactService]
  private val contactHandler = new ContactHandler(contactService)
  private val route          = contactHandler.route

  "ContactHandler" should {}
}
