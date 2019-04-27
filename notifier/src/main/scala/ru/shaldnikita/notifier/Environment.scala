package ru.shaldnikita.notifier

import akka.actor.{ActorSystem, Props, SupervisorStrategy}
import akka.routing.FromConfig
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import ru.shaldnikita.notifier.actors.NotificationsKeeper
import ru.shaldnikita.notifier.actors.notifiers.NotifyManager
import ru.shaldnikita.notifier.port.adapter.dao.repositories.UserRepository
import ru.shaldnikita.notifier.port.adapter.notifiers.email.conf.{EmailConfiguration, SystemEnvironmentEmailConfiguration}
import ru.shaldnikita.notifier.port.adapter.notifiers.email.{EmailBuilder, EmailNotifier}
import ru.shaldnikita.notifier.port.adapter.notifiers.phone.PhoneNotifier
import ru.shaldnikita.notifier.port.adapter.notifiers.phone.conf.{PhoneConfiguration, SystemEnvironmentPhoneConfiguration}
import ru.shaldnikita.notifier.port.adapter.web.NotificationsEndpoint
import ru.shaldnikita.notifier.service.UserService

import scala.concurrent.ExecutionContext

object Environment {

  import org.apache.log4j.BasicConfigurator

  BasicConfigurator.configure()

  implicit val system: ActorSystem = ActorSystem("reminder")
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  val conf = ConfigFactory.defaultApplication()

  implicit val emailNotificationsConfiguration: EmailConfiguration =
    new SystemEnvironmentEmailConfiguration(conf.getConfig("email").resolve())

  val mailBuilder = new EmailBuilder
  val emailNotificator = new EmailNotifier(mailBuilder)

  implicit val phoneNotificationsConfiguration: PhoneConfiguration =
    new SystemEnvironmentPhoneConfiguration(conf.getConfig("sms").resolve())
  val phoneNotificator = new PhoneNotifier()

  val userRepository = new UserRepository(inMemoryDatabase)
  val userService = new UserService(userRepository)

  val notifiersSupervisionStrategy = SupervisorStrategy.defaultStrategy
  val notifier = system.actorOf(FromConfig(supervisorStrategy = notifiersSupervisionStrategy).props(
    Props(new NotifyManager(List(phoneNotificator, emailNotificator), userService))), NotifyManager.name)

  val endpoint = new NotificationsEndpoint()

  val notifyKeeper = system.actorOf(FromConfig.props(Props(new NotificationsKeeper(notifier))), NotificationsKeeper.name)
}
