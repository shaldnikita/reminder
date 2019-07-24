package ru.shaldnikita

import akka.http.scaladsl.Http
import org.slf4j.LoggerFactory

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
object Main extends App {
  private val log = LoggerFactory.getLogger(this.getClass)

  import Environment._

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  bindingFuture
    .map { serverBinding =>
      log.info(s"Bound to ${serverBinding.localAddress} ")
    }
    .recover {
      case ex: Throwable =>
        log.error("Failed to bind to {}:{}!", "host", 8080, ex)
        system.terminate()
        ()
    }
}
