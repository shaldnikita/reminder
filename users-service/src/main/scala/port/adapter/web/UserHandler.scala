package port.adapter.web

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import application.UserService
import port.adapter.web.models.UserModel

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserHandler(userService: UserService)(implicit ec: ExecutionContext)
    extends BaseHandler {

  val route: Route = pathPrefix("users") {
    entity(as[UserModel]) { user =>
      post(
        complete(userService.createUser(UserModel.toDomain(user)))
      ) ~ put(
        complete(userService.updateUser(UserModel.toDomain(user)))
      )
    } ~
      get {
        path(JavaUUID)(userId => userService.findUser(userId.toString))
      }

  }

}
