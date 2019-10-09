package ru.shaldnikita.users.users

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import ru.shaldnikita.users.application.{ContactService, UserService}
import ru.shaldnikita.users.port.adapter.dao.Database
import ru.shaldnikita.users.port.adapter.dao.contact.ContactDaoImpl
import ru.shaldnikita.users.port.adapter.dao.user.UserDaoImpl
import ru.shaldnikita.users.port.adapter.web.handlers.MainHandler
import ru.shaldnikita.users.port.adapter.web.handlers.contacts.ContactHandler
import ru.shaldnikita.users.port.adapter.web.handlers.users.UserHandler
import ru.shaldnikita.users.users.application.{ContactService, UserService}
import ru.shaldnikita.users.users.port.adapter.dao.Database
import ru.shaldnikita.users.users.port.adapter.dao.contact.ContactDaoImpl
import ru.shaldnikita.users.users.port.adapter.dao.user.UserDaoImpl
import ru.shaldnikita.users.users.port.adapter.web.handlers.MainHandler
import ru.shaldnikita.users.users.port.adapter.web.handlers.contacts.ContactHandler
import ru.shaldnikita.users.users.port.adapter.web.handlers.users.UserHandler

import scala.concurrent.ExecutionContext

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
object Environment {
  implicit val system: ActorSystem  = ActorSystem("users")
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val actorMaterializer: ActorMaterializer =
    ActorMaterializer()

  Database
  val database = Database.db

  private val userDao    = new UserDaoImpl
  private val contactDao = new ContactDaoImpl

  private val userService    = new UserService(userDao, contactDao, database)
  private val contactService = new ContactService(contactDao, database)

  private val userHandler    = new UserHandler(userService)
  private val contactHandler = new ContactHandler(contactService)

  def route = MainHandler.route(userHandler, contactHandler)
}
