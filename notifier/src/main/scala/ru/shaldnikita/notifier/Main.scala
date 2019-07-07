package ru.shaldnikita.notifier

import java.util.concurrent.TimeUnit

import akka.http.scaladsl.Http
import Environment._
import akka.event.Logging
import ru.shaldnikita.notifier.domain.models.notifications.Notification

import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 02.04.2019
  */
object Main extends App {

  override def main(args: Array[String]): Unit = {
    Environment
    val log = Logging(system.eventStream, "reminder")

    val bindingFuture =
      Http().bindAndHandle(Environment.endpoint.route, "localhost", 8080)
    bindingFuture
      .map { serverBinding =>
        log.info(s"Bound to ${serverBinding.localAddress} ")
      }
      .onFailure {
        case ex: Exception =>
          log.error(ex, "Failed to bind to {}:{}!", "host", 8080)
          system.terminate()
      }
    Environment.notifyKeeper ! Notification(
      text = "asd",
      notifyIn = FiniteDuration.apply(1, TimeUnit.SECONDS),
      isWholeDay = false,
      isRepeatable = false,
      repeatIn = None,
      userId = "Ya"
    )
  }

}
