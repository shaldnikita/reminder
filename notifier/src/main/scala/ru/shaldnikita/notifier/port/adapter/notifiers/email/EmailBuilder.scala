package ru.shaldnikita.notifier.port.adapter.notifiers.email

import org.apache.commons.mail._
import ru.shaldnikita.notifier.port.adapter.notifiers.email.MailType._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class EmailBuilder {

  def buildEmail(message: MailMessage): Email = {
    val format = getFormat(message)
    val mail = format match {
      case Plain => new SimpleEmail().setMsg(message.message)
      case Rich => new HtmlEmail().setHtmlMsg(message.richMessage.get).setTextMsg(message.message)
      case MultiPart =>
        val attachment = new EmailAttachment()
        attachment.setPath(message.attachment.get.getAbsolutePath)
        attachment.setDisposition(EmailAttachment.ATTACHMENT)
        attachment.setName(message.attachment.get.getName)
        new MultiPartEmail().attach(attachment).setMsg(message.message)
    }

    message.to foreach mail.addTo
    message.cc foreach mail.addCc
    message.bcc foreach mail.addBcc
    mail.setFrom(message.from._1, message.from._2)
    mail.setSubject(message.subject)
    mail
  }

  private def getFormat(message: MailMessage) = {
    if (message.attachment.isDefined)
      MultiPart
    else if (message.richMessage.isDefined)
      Rich
    else Plain
  }
}
