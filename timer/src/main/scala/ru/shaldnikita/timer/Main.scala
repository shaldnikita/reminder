package ru.shaldnikita.timer

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import akka.routing.{BalancingPool, FromConfig}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import ru.shaldnikita.timer.actors.{Notifier, NotifyKeeper}
import ru.shaldnikita.timer.entities.{Notification, Owner}
import ru.shaldnikita.timer.notificators.email.EmailNotificator
import ru.shaldnikita.timer.notificators.email.conf.SystemEnvironmentEmailConfiguration
import ru.shaldnikita.timer.service.UserService

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 02.04.2019
  */
object Main extends App {

  override def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("reminder")
    implicit val ec: ExecutionContext = system.dispatcher
    implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()

    implicit val conf = new SystemEnvironmentEmailConfiguration(ConfigFactory.defaultApplication())


    val notifier = system.actorOf(BalancingPool(10).props(Props(new Notifier(List(new EmailNotificator()), new UserService()))))
    val ref = system.actorOf(FromConfig.props(Props(new NotifyKeeper(notifier))), NotifyKeeper.name)

    ref ! Notification(
      text = "asd",
      notifyIn = FiniteDuration.apply(1, TimeUnit.SECONDS),
      isWholeDay = false,
      isRepeatable = false,
      repeatIn = None,
      owner = Owner("Ya")
    )
  }

}