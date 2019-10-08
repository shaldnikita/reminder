package ru.shaldnikita.users.port.adapter.web.handlers.users

import java.util.UUID

import akka.Done
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import ru.shaldnikita.users.application.UserService
import ru.shaldnikita.users.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.users.port.adapter.web.models.users.{CreateUserModel, UserModel}
import ru.shaldnikita.users.port.adapter.web.validators.users.{CreateUserModelValidator, UserModelValidator}
import ru.shaldnikita.users.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.users.port.adapter.web.models.users.{CreateUserModel, UserModel}
import ru.shaldnikita.users.port.adapter.web.validators.users.{CreateUserModelValidator, UserModelValidator}
import ru.shaldnikita.users.application.UserService
import ru.shaldnikita.users.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.users.port.adapter.web.models.users.{CreateUserModel, UserModel}
import ru.shaldnikita.users.port.adapter.web.validators.users.{CreateUserModelValidator, UserModelValidator}
import ru.shaldnikita.users.port.adapter.web.handlers.BaseHandler
import ru.shaldnikita.users.port.adapter.web.models.users.{CreateUserModel, UserModel}
import ru.shaldnikita.users.port.adapter.web.validators.users.{CreateUserModelValidator, UserModelValidator}
import ru.shaldnikita.users.application.UserService

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserHandler(userService: UserService)(implicit ec: ExecutionContext)
    extends BaseHandler {

  val routes: Route = pathPrefix("users") {
    pathEndOrSingleSlash {
      get(findAll) ~
        postEntity(as[CreateUserModel], CreateUserModelValidator)(createUser) ~
        putEntity(as[UserModel], UserModelValidator)(updateUser)
    } ~
      getById(findOne) ~
      deleteById(deleteUser)
  }

  def deleteUser(userId: UUID) = {
    complete(
      userService
        .deleteUser(userId.toString)
        .map(_ => Done)
    )
  }

  private def findAll = {
    complete(userService.findUsers().map(_.map(UserModel.apply)))
  }

  private def createUser(user: CreateUserModel) = {
    complete(
      userService
        .createUser(CreateUserModel.toDomain(user))
        .map(user => StatusCodes.Created -> UserModel.apply(user))
    )
  }

  private def updateUser(user: UserModel) = {
    complete(
      userService
        .updateUser(UserModel.toDomain(user))
        .map(UserModel.apply)
    )
  }

  private def findOne(userId: UUID) = {
    complete(
      userService
        .findUser(userId.toString)
        .map(UserModel.apply)
    )
  }
}
