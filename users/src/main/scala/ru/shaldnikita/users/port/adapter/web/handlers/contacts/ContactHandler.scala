package ru.shaldnikita.users.port.adapter.web.handlers.contacts

import java.util.UUID

import akka.Done
import akka.http.scaladsl.server.{Route, StandardRoute}
import ru.shaldnikita.users.application.ContactService
import ru.shaldnikita.users.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.users.port.adapter.web.models.contacts.{ContactModel, CreateContactModel}
import ru.shaldnikita.users.port.adapter.web.validators.contacts.{ContactModelValidator, CreateContactModelValidator}
import ru.shaldnikita.users.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.users.port.adapter.web.models.contacts.{ContactModel, CreateContactModel}
import ru.shaldnikita.users.port.adapter.web.validators.contacts.{ContactModelValidator, CreateContactModelValidator}
import ru.shaldnikita.users.application.ContactService
import ru.shaldnikita.users.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.users.port.adapter.web.models.contacts.{ContactModel, CreateContactModel}
import ru.shaldnikita.users.port.adapter.web.validators.contacts.{ContactModelValidator, CreateContactModelValidator}
import ru.shaldnikita.users.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.users.port.adapter.web.models.contacts.{ContactModel, CreateContactModel}
import ru.shaldnikita.users.port.adapter.web.validators.contacts.{ContactModelValidator, CreateContactModelValidator}
import ru.shaldnikita.users.application.ContactService

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
class ContactHandler(contactService: ContactService)(
    implicit ec: ExecutionContext
) extends BaseHandler {
  override def routes: Route = path("users" / JavaUUID / "contacts") { userId =>
    get {
      getUserContacts(userId)
    } ~
      postEntity(as[CreateContactModel], CreateContactModelValidator) {
        contact =>
          createContact(contact)
      } ~
      putEntity(as[ContactModel], ContactModelValidator) { contact =>
        updateContact(contact)
      } ~
      delete {
        pathEndOrSingleSlash {
          deleteUserContacts(userId)
        } ~
          path(JavaUUID) { contactId =>
            deleteContact(contactId)
          }
      }
  }

  def getUserContacts(userId: UUID) = {
    complete(
      contactService
        .findUserContacts(userId.toString)
        .map(_.map(ContactModel.apply))
    )
  }

  private def createContact(contact: CreateContactModel): StandardRoute = {
    complete(
      contactService
        .create(CreateContactModel.toDomain(contact))
        .map(ContactModel.apply)
    )
  }

  private def updateContact(contact: ContactModel) = {
    complete(
      contactService
        .update(ContactModel.toDomain(contact))
        .map(ContactModel.apply)
    )
  }

  def deleteContact(contactId: UUID) = {
    complete(contactService.delete(contactId.toString).map(_ => Done))
  }

  def deleteUserContacts(userId: UUID) = {
    complete(
      contactService.deleteAllUserContacts(userId.toString).map(_ => Done)
    )
  }

}
