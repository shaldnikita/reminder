package ru.shaldnikita.users.port.adapter.web.models.users

import ru.shaldnikita.users.application.models.users.CreateUser
import ru.shaldnikita.users.port.adapter.web.models.DtoModel
import ru.shaldnikita.users.port.adapter.web.models.contacts.ContactModel
import ru.shaldnikita.users.port.adapter.web.models.contacts.ContactModel
import ru.shaldnikita.users.application.models.users.CreateUser
import ru.shaldnikita.users.port.adapter.web.models.contacts.ContactModel
import ru.shaldnikita.users.port.adapter.web.validators.users.CreateUserModelValidator
import ru.shaldnikita.users.port.adapter.web.models.contacts.ContactModel
import ru.shaldnikita.users.application.models.users.CreateUser
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
    CreateUser(firstName, secondName, contacts.map(ContactModel.toDomain))
  }

  override def validator = CreateUserModelValidator
}
