package ru.shaldnikita.port.adapter.web.models.dto.contacts

import ru.shaldnikita.application.models.contacts.CreateContact
import ru.shaldnikita.domain.exceptions.UsersServiceException
import ru.shaldnikita.domain.models.contacts.ContactType
import ru.shaldnikita.port.adapter.web.models.dto.DtoModel
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

}
