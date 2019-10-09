package ru.shaldnikita.users.users.port.adapter.web.models.contacts

import ru.shaldnikita.port.adapter.web.models.DtoModel
import ru.shaldnikita.users.application.models.contacts.CreateContact
import ru.shaldnikita.users.domain.exceptions.UsersServiceException
import ru.shaldnikita.users.domain.models.contacts.ContactType
import ru.shaldnikita.users.port.adapter.web.models.DtoModel
import ru.shaldnikita.users.port.adapter.web.validators.contacts.CreateContactModelValidator
import ru.shaldnikita.users.users.application.models.contacts.CreateContact
import ru.shaldnikita.users.users.domain.models.contacts.ContactType
import ru.shaldnikita.users.users.port.adapter.web.validators.contacts.CreateContactModelValidator
import spray.json.RootJsonFormat

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
case class CreateContactModel(`type`: String, value: String, userId: String)

object CreateContactModel extends DtoModel[CreateContactModel, CreateContact] {
  override def jsonFormat: RootJsonFormat[CreateContactModel] =
    jsonFormat3(CreateContactModel.apply)

  //TODO toApplicaiton here
  override def toDomain(model: CreateContactModel) = {
    import model._
    val contactType = ContactType.ofId(`type`)
    contactType
      .map(CreateContact(_, value, userId))
      .getOrElse(throw UsersServiceException(s"Unexpected type ${`type`}"))
  }

  override def validator = CreateContactModelValidator
}
