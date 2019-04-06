package ru.shaldnikita.notifier.notificators.email

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 25.03.2019
  */
sealed abstract class MailType

object MailType {

  case object Plain extends MailType

  case object Rich extends MailType

  case object MultiPart extends MailType

}


