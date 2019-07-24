package ru.shaldnikita.port.adapter.web.directives

import akka.http.scaladsl.server.Directives

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
trait BaseDirectives
    extends Directives
    with ValidationDirectives
    with CommonDirectives
