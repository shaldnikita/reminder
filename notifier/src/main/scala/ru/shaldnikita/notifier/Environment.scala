package ru.shaldnikita.notifier

import akka.actor.{ActorSystem, Props}
import akka.routing.{BalancingPool, FromConfig}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import ru.shaldnikita.notifier.actors.{Notifier, NotifyKeeper}
import ru.shaldnikita.notifier.dao.repositories.UserRepository
import ru.shaldnikita.notifier.notificators.email.EmailNotificator
import ru.shaldnikita.notifier.notificators.email.conf.{EmailConfiguration, SystemEnvironmentEmailConfiguration}
import ru.shaldnikita.notifier.notificators.phone.PhoneNotificator
import ru.shaldnikita.notifier.notificators.phone.conf.{PhoneConfiguration, SystemEnvironmentPhoneConfiguration}
import ru.shaldnikita.notifier.service.UserService

import scala.concurrent.ExecutionContext

object Environment {

  implicit val system: ActorSystem = ActorSystem("reminder")
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  val conf = ConfigFactory.defaultApplication()

  implicit val emailNotificationsConfiguration: EmailConfiguration = new SystemEnvironmentEmailConfiguration(conf)
  val emailNotificator = new EmailNotificator()

  implicit val phoneNotificationsConfiguration: PhoneConfiguration = new SystemEnvironmentPhoneConfiguration(conf)
  val phoneNotificator = new PhoneNotificator()

  val userRepository = new UserRepository(dao.dao.inMemoryDatabase)
  val userService = new UserService(userRepository)


  val notifier = system.actorOf(BalancingPool(10).props(Props(
    new Notifier(List(emailNotificator, phoneNotificator), userService))))

  val notifyKeeper = system.actorOf(FromConfig.props(Props(new NotifyKeeper(notifier))), NotifyKeeper.name)

}
