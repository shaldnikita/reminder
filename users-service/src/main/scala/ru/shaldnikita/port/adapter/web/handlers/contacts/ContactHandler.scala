package ru.shaldnikita.port.adapter.web.handlers.contacts

import akka.http.scaladsl.server.Route
import ru.shaldnikita.application.ContactService
import ru.shaldnikita.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.port.adapter.web.models.dto.contacts.{
  ContactModel,
  CreateContactModel
}
import ru.shaldnikita.port.adapter.web.validation.{
  ContactModelValidator,
  CreateContactModelValidator
}

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
class ContactHandler(contactService: ContactService)(
    implicit ec: ExecutionContext)
    extends BaseHandler {
  override def routes: Route = path("users" / JavaUUID) { userId =>
    get {
      complete(
        contactService
          .findUserContacts(userId.toString)
          .map(_.map(ContactModel.toModel)))
    } ~
      (post & validatedEntity(as[CreateContactModel],
                              CreateContactModelValidator.validate)) {
        contact =>
          createContact(contact)
      } ~
      (put & validatedEntity(as[ContactModel], ContactModelValidator.validate)) {
        contact =>
          updateContact(contact)
      } ~
      delete {
        complete("ok")
      }
  }

  private def createContact(contact: CreateContactModel) = {
    complete(
      contactService
        .create(CreateContactModel.toDomain(contact))
        .map(ContactModel.toModel))
  }

  private def updateContact(contact: ContactModel) = {
    complete(
      contactService
        .update(ContactModel.toDomain(contact))
        .map(ContactModel.toModel))
  }
}
