package ru.shaldnikita.port.adapter.web.models.dto.users

import cats.implicits._
import ru.shaldnikita.domain.models.users.User
import ru.shaldnikita.port.adapter.web.models.dto.DtoModel
import ru.shaldnikita.port.adapter.web.models.dto.contacts.ContactModel
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
    User(
      userId,
      firstName,
      secondName,
      contacts.map(ContactModel.toDomain)
    )
  }

  override def toModel(domain: User) = {
    import domain._
    UserModel(
      userId,
      firstName,
      secondName,
      contacts.map(ContactModel.toModel)
    )
  }
}
