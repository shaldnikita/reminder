package ru.shaldnikita.users.port.adapter.web.handlers

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
trait MainHandler {
  def route(handlers: BaseHandler*): Route
}

object MainHandler extends MainHandler {

  def route(handlers: BaseHandler*): Route = {

    handlers
      .map { handler =>
        val handlerName = handler.getClass.getSimpleName
        logRequestResult(handlerName) {
          handler.route
        }
      }
      .reduce((a, b) => a ~ b)

  }

}
