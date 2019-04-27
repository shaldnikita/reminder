package ru.shaldnikita.notifier.port.adapter.notifiers.email

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 25.03.2019
  */
case class MailMessage(from: (String, String), // (email -> name)
                       to: Seq[String],
                       cc: Seq[String] = Seq.empty,
                       bcc: Seq[String] = Seq.empty,
                       subject: String,
                       message: String,
                       richMessage: Option[String] = None,
                       attachment: Option[java.io.File] = None)
