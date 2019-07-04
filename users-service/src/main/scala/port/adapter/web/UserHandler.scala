package port.adapter.web

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.JavaUUID
import akka.http.scaladsl.server.Route
import port.adapter.dao.user.UserDao

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class UserHandler(userDao: UserDao) {

  val route: Route = pathPrefix("users" / JavaUUID) { userId =>
    get {
        complete()
    }
  }


}
