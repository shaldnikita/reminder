package ru.shaldnikita.port.adapter.web.handlers.users

import java.util.UUID

import akka.Done
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import ru.shaldnikita.application.UserService
import ru.shaldnikita.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.port.adapter.web.models.dto.users.{
  CreateUserModel,
  UserModel
}
import ru.shaldnikita.port.adapter.web.validation.{
  CreateUserModelValidator,
  UserModelValidator
}

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserHandler(userService: UserService)(implicit ec: ExecutionContext)
    extends BaseHandler {

  val routes: Route = pathPrefix("users") {

    pathEndOrSingleSlash {
      get {
        findAll
      } ~
        (put & validatedEntity(as[UserModel], UserModelValidator.validate))(
          user => updateUser(user)) ~ (post &
        validatedEntity(as[CreateUserModel],
                        CreateUserModelValidator.validate))(user =>
        createUser(user))
    } ~
      path(JavaUUID) { userId =>
        get {
          findOne(userId)
        } ~
          delete {
            deleteUser(userId)
          }
      }
  }

  def deleteUser(userId: UUID) = {
    complete(
      userService
        .deleteUser(userId.toString)
        .map(_ => Done))
  }

  private def findAll = {
    complete(userService.findUsers().map(_.map(UserModel.toModel)))
  }

  private def createUser(user: CreateUserModel) = {
    complete(
      userService
        .createUser(CreateUserModel.toDomain(user))
        .map(user => StatusCodes.Created -> UserModel.toModel(user)))
  }

  private def updateUser(user: UserModel) = {
    complete(
      userService
        .updateUser(UserModel.toDomain(user))
        .map(UserModel.toModel)
    )
  }

  private def findOne(userId: UUID) = {
    complete(
      userService
        .findUser(userId.toString)
        .map(UserModel.toModel)
    )
  }
}
