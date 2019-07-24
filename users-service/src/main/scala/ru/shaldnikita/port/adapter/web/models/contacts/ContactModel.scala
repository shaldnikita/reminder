package ru.shaldnikita.port.adapter.web.models.contacts

import ru.shaldnikita.domain.exceptions.UsersServiceException
import ru.shaldnikita.domain.models.contacts.{Contact, ContactType}
import ru.shaldnikita.port.adapter.web.models.{BaseModel, DtoModel}
import ru.shaldnikita.port.adapter.web.validators.contacts.ContactModelValidator
import spray.json.RootJsonFormat

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
case class ContactModel(contactId: String,
                        `type`: String,
                        value: String,
                        userId: String)

object ContactModel
    extends DtoModel[ContactModel, Contact]
    with BaseModel[ContactModel] {

  override def jsonFormat: RootJsonFormat[ContactModel] =
    jsonFormat4(ContactModel.apply)

  override def toDomain(model: ContactModel) = {
    import model._
    val contactType = ContactType.ofId(`type`)
    contactType
      .map(Contact(contactId, _, value, userId))
      .getOrElse(throw UsersServiceException(s"Unexpected type ${`type`}"))
  }

  def apply(domain: Contact): ContactModel = {
    import domain._
    ContactModel(contactId, `type`.id, value, userId)
  }

  override def validator = ContactModelValidator
}
