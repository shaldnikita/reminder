package ru.shaldnikita.notifier.port.adapter.notifiers.email

import ru.shaldnikita.notifier.domain.models.notifications.NotificationPayload
import ru.shaldnikita.notifier.domain.models.users.UserContactType
import ru.shaldnikita.notifier.domain.notifiers.Notifier
import ru.shaldnikita.notifier.port.adapter.notifiers.email.conf.EmailConfiguration

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 25.03.2019
  */
class EmailNotifier(emailBuilder: EmailBuilder)
                   (implicit conf: EmailConfiguration,
                    ec: ExecutionContext) extends Notifier {


  override def doNotify(contacts: Seq[UserContactType], notificationPayload: NotificationPayload)
                       (implicit ec: ExecutionContext): Future[Unit] = {

  }

  def requiredContactTypes: Seq[UserContactType] = Seq(UserContactType.Email, UserContactType.VkPage)


  override def notificationType = NotificationType.
}


override def notify (user: User, notification: NotificationContent) (implicit ec: ExecutionContext): Future[Unit] = Future {

  import ru.shaldnikita.notifier.domain.models.notifications.NotificationPayload

  val message = MailMessage (
  from = (conf.fromEmail, conf.fromName),
  to = Seq (user.email),
  subject = conf.subject,
  message = notification.text
  )
  val email = emailBuilder.buildEmail (message)
  val enrichedEmailMessage = enrichEmail (email)
  enrichedEmailMessage.send ()
}


  private def enrichEmail (email: Email) = {
  email.setSSLOnConnect (conf.ssl)
  email.setAuthentication (conf.login, conf.password)
  email.setSmtpPort (conf.port)
  email.setHostName (conf.hostName)
  email
}

  override def notificationType = EmailNotification
}
