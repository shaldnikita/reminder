package ru.shaldnikita.port.adapter.web

import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ru.shaldnikita.application.UserService
import ru.shaldnikita.port.adapter.web.models.UserModel

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserHandler(userService: UserService)(implicit ec: ExecutionContext)
    extends BaseHandler
    with SprayJsonSupport {

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
    complete(userService.deleteUser(userId.toString).map(_ => StatusCodes.OK))
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
        .map {
          case Some(user) =>
            StatusCodes.OK -> UserModel.toModel(user): ToResponseMarshallable
          case None => StatusCodes.NotFound: ToResponseMarshallable
        }
    )
  }

  private def findOne(userId: UUID) = {
    complete(
      userService.findUser(userId.toString).map {
        case Some(user) =>
          StatusCodes.OK -> UserModel.toModel(user): ToResponseMarshallable
        case None => StatusCodes.NotFound: ToResponseMarshallable
      }
    )
  }
}
