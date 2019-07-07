package ru.shaldnikita
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import ru.shaldnikita.application.{ContactService, UserService}
import ru.shaldnikita.port.adapter.dao.contact.ContactDao
import ru.shaldnikita.port.adapter.dao.Database
import ru.shaldnikita.port.adapter.dao.user.UserDao
import ru.shaldnikita.port.adapter.web.handlers.contacts.ContactHandler
import ru.shaldnikita.port.adapter.web.handlers.MainHandler
import ru.shaldnikita.port.adapter.web.handlers.users.UserHandler

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
object Environment {
  implicit val system: ActorSystem  = ActorSystem("users-service")
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val actorMaterializer: ActorMaterializer =
    ActorMaterializer()

  Database
  private implicit val database = Database.db

  private val userDao    = new UserDao
  private val contactDao = new ContactDao

  private val userService    = new UserService(userDao, contactDao)
  private val contactService = new ContactService(contactDao)

  private val userHandler    = new UserHandler(userService)
  private val contactHandler = new ContactHandler(contactService)

  def route = MainHandler.route(userHandler, contactHandler)
}