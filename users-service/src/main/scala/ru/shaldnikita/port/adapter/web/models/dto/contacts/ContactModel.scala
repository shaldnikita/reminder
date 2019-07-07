package ru.shaldnikita.port.adapter.web.models.dto.contacts

import cats.implicits._
import ru.shaldnikita.domain.exceptions.UsersServiceException
import ru.shaldnikita.domain.models.contacts.{Contact, ContactType}
import ru.shaldnikita.port.adapter.web.models.dto.DtoModel
import spray.json.RootJsonFormat

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
case class ContactModel(contactId: Option[String],
                        `type`: String,
                        value: String,
                        userId: String)

object ContactModel extends DtoModel[ContactModel, Contact] {
  override def jsonFormat: RootJsonFormat[ContactModel] =
    jsonFormat4(ContactModel.apply)

  override def toDomain(model: ContactModel) = {
    import model._
    val contactType = ContactType.ofId(`type`)
    contactType
      .map(Contact(_, value, userId, contactId))
      .getOrElse(throw UsersServiceException(s"Unexpected type ${`type`}"))
  }

  override def toModel(domain: Contact) = {
    import domain._
    ContactModel(
      contactId.some,
      `type`.id,
      value,
      userId
    )
  }
}
