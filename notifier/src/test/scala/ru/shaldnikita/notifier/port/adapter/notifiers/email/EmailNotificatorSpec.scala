package ru.shaldnikita.notifier.port.adapter.notifiers.email

import com.typesafe.config.ConfigFactory
import org.apache.commons.mail.SimpleEmail
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures
import ru.shaldnikita.notifier.SpecBase
import ru.shaldnikita.notifier.port.adapter.dao.entities.User
import ru.shaldnikita.notifier.port.adapter.notifiers.email.conf.SystemEnvironmentEmailConfiguration

import scala.concurrent.ExecutionContext.Implicits.global
/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class EmailNotificatorSpec extends SpecBase{

  private val mailBuilder = mock[EmailBuilder]
  private val config =  ConfigFactory.defaultApplication().getConfig("email").resolve()
  private implicit val emailConfig = new SystemEnvironmentEmailConfiguration(config)
  private val mail = mock[SimpleEmail]
  private val notificator = new EmailNotifier(mailBuilder)

  "EmailNotificator" should "send letter" in {
    val user = User("test@mail.ru", "john", "snow", "123", "123")
    val expectedMessage = MailMessage(
      from = (emailConfig.fromEmail, emailConfig.fromName),
      to = Seq(user.email),
      subject = "Notification",
      message = "Уважаемый hujjj"
    )

    (mailBuilder.buildEmail _)
      .expects(expectedMessage)
        .returning(mail)
    (mail.addTo())
    (mail.setFrom())
    (mail.send _)
        .expects()
        .returning("ok")
    notificator.notify(user, ).futureValue shouldBe ()
  }

}
