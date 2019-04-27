package ru.shaldnikita.notifier.port.adapter.notifiers.email

import org.apache.commons.mail._
import ru.shaldnikita.notifier.domain.messages.{NotificationContent, User}
import ru.shaldnikita.notifier.port.adapter.notifiers.email.conf.EmailConfiguration
import ru.shaldnikita.notifier.port.adapter.notifiers.{EmailNotifier, NotifierActor}

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 25.03.2019
  */
class EmailNotifier(emailBuilder: EmailBuilder)
                   (implicit conf: EmailConfiguration,
                       ec: ExecutionContext) extends NotifierActor {

  override def notify(user: User, notification: NotificationContent)(implicit ec: ExecutionContext): Future[Unit] = Future {
    val message = MailMessage(
      from = (conf.fromEmail, conf.fromName),
      to = Seq(user.email),
      subject = conf.subject,
      message = notification.text
    )
    val email = emailBuilder.buildEmail(message)
    val enrichedEmailMessage = enrichEmail(email)
    enrichedEmailMessage.send()
  }

  private def enrichEmail(email: Email) = {
    email.setSSLOnConnect(conf.ssl)
    email.setAuthentication(conf.login, conf.password)
    email.setSmtpPort(conf.port)
    email.setHostName(conf.hostName)
    email
  }

  override def notificationType = EmailNotifier
}
