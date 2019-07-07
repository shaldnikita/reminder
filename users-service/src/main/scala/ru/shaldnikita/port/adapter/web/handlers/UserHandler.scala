package ru.shaldnikita.port.adapter.web.handlers

import java.util.UUID

import akka.Done
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import ru.shaldnikita.application.UserService
import ru.shaldnikita.port.adapter.web.BaseHandler
import ru.shaldnikita.port.adapter.web.WebUtils._
import ru.shaldnikita.port.adapter.web.models.UserModel

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserHandler(userService: UserService)(implicit ec: ExecutionContext)
    extends BaseHandler {

  val route: Route = pathPrefix("users") {
    pathEndOrSingleSlash {
      get {
        findAll
      } ~
        entity(as[UserModel]) { user =>
          post(
            createUser(user)
          ) ~
            put(
              updateUser(user)
            )
        }
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
        .map(_.map(_ => Done)
          .toResponse(StatusCodes.OK)))
  }

  private def findAll = {
    complete(userService.findUsers().map(_.map(UserModel.toModel)))
  }

  private def createUser(user: UserModel) = {
    complete(
      userService
        .createUser(UserModel.toDomain(user))
        .map(user => StatusCodes.Created -> UserModel.toModel(user)))
  }

  private def updateUser(user: UserModel) = {
    complete(
      userService
        .updateUser(UserModel.toDomain(user))
        .map(_.map(UserModel.toModel))
        .map(_.toResponse(StatusCodes.OK))
    )
  }

  private def findOne(userId: UUID) = {
    complete(
      userService
        .findUser(userId.toString)
        .map(_.map(UserModel.toModel))
        .map(_.toResponse(StatusCodes.OK))
    )
  }
}
