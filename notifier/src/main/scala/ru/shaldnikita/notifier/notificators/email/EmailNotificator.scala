package ru.shaldnikita.notifier.notificators.email

import org.apache.commons.mail._
import ru.shaldnikita.notifier.dao.entities.User
import ru.shaldnikita.notifier.notificators.Notificator
import ru.shaldnikita.notifier.notificators.email.MailType.{MultiPart, Plain, Rich}
import ru.shaldnikita.notifier.notificators.email.conf.EmailConfiguration

import scala.async.Async._
import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 25.03.2019
  */
class EmailNotificator(implicit conf: EmailConfiguration) extends Notificator {

  override def notify(user: User)(implicit ec: ExecutionContext): Future[Unit] = async {
    send(MailMessage(
      from = (conf.fromEmail, conf.fromName),
      to = Seq(user.email),
      subject = "Notification",
      message = "Уважаемый hujjj"
    ))
  }

  def send(message: MailMessage)(implicit conf: EmailConfiguration) {

    val format = getFormat(message)
    val commonsMail: Email = buildMail(format, message)

    message.to foreach commonsMail.addTo
    message.cc foreach commonsMail.addCc
    message.bcc foreach commonsMail.addBcc
    commonsMail.setAuthentication(conf.login, conf.password)
    commonsMail.setHostName("smtp.yandex.com")
    commonsMail.setSmtpPort(465)
    commonsMail.setSSLOnConnect(true)

    commonsMail
      .setFrom(message.from._1, message.from._2)
      .setSubject(message.subject)
      .send()
  }

  private def buildMail(format: MailType, message: MailMessage) = {
    format match {
      case Plain => new SimpleEmail().setMsg(message.message)
      case Rich => new HtmlEmail().setHtmlMsg(message.richMessage.get).setTextMsg(message.message)
      case MultiPart =>
        val attachment = new EmailAttachment()
        attachment.setPath(message.attachment.get.getAbsolutePath)
        attachment.setDisposition(EmailAttachment.ATTACHMENT)
        attachment.setName(message.attachment.get.getName)
        new MultiPartEmail().attach(attachment).setMsg(message.message)
    }
  }

  private def getFormat(message: MailMessage) = {
    if (message.attachment.isDefined)
      MultiPart
    else if (message.richMessage.isDefined)
      Rich
    else Plain
  }
}
