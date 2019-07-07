package ru.shaldnikita.port.adapter.web.models.dto.users

import ru.shaldnikita.application.models.users.CreateUser
import ru.shaldnikita.port.adapter.web.models.dto.DtoModel
import ru.shaldnikita.port.adapter.web.models.dto.contacts.ContactModel
import spray.json.RootJsonFormat

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
case class CreateUserModel(firstName: String,
                           secondName: String,
                           contacts: List[ContactModel])

object CreateUserModel extends DtoModel[CreateUserModel, CreateUser] {
  override def jsonFormat: RootJsonFormat[CreateUserModel] =
    jsonFormat3(CreateUserModel.apply)

  //TODO toApplication here
  override def toDomain(model: CreateUserModel) = {
    import model._
    CreateUser(
      firstName,
      secondName,
      contacts.map(ContactModel.toDomain)
    )
  }

}
