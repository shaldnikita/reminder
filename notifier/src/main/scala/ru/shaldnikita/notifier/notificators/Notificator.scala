package ru.shaldnikita.notifier.notificators

import ru.shaldnikita.notifier.dao.entities.User

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.04.2019
  */
trait Notificator {
  def notify(user: User)(implicit ec: ExecutionContext): Future[Unit]
}
