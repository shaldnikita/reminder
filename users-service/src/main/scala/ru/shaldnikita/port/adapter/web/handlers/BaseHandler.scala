package ru.shaldnikita.port.adapter.web.handlers

import akka.http.scaladsl.server.Route
import ru.shaldnikita.port.adapter.web.directives.BaseDirectives
import ru.shaldnikita.port.adapter.web.protocols.BaseProtocols

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 06.07.2019
  */
trait BaseHandler extends BaseDirectives with BaseProtocols {

  def route: Route = withExceptionsHandling(
    withRejectionsHandling(
      routes
    )
  )

  protected def routes: Route

}
