package ru.shaldnikita.port.adapter.web.models.users

import ru.shaldnikita.domain.models.users.User
import ru.shaldnikita.port.adapter.web.models.DtoModel
import ru.shaldnikita.port.adapter.web.models.contacts.ContactModel
import ru.shaldnikita.port.adapter.web.validators.users.UserModelValidator
import spray.json.RootJsonFormat

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
case class UserModel(userId: String,
                     firstName: String,
                     secondName: String,
                     contacts: List[ContactModel])

object UserModel extends DtoModel[UserModel, User] {

  override def jsonFormat: RootJsonFormat[UserModel] =
    jsonFormat4(UserModel.apply)

  override def toDomain(model: UserModel) = {
    import model._
    User(userId, firstName, secondName, contacts.map(ContactModel.toDomain))
  }

  def apply(domain: User): UserModel = {
    import domain._
    UserModel(userId, firstName, secondName, contacts.map(ContactModel.apply))
  }

  override def validator = UserModelValidator
}
