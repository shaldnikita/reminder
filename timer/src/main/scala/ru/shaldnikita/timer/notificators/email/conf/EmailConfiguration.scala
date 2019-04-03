package ru.shaldnikita.timer.notificators.email.conf

import com.typesafe.config.Config

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 25.03.2019
  */
trait EmailConfiguration {
  def fromEmail: String

  def fromName: String

  def login: String

  def password: String

  def hostName: String

  def port: Int

  def ssl: Boolean
}

class SystemEnvironmentEmailConfiguration(config: Config) extends EmailConfiguration {
  override def fromEmail = config.getString("fromEmail")

  override def fromName = config.getString("fromName")

  override def login = config.getString("login")

  override def password = config.getString("password")

  override def hostName = sys.env.getOrElse("EMAIL_HOST", "smtp.yandex.com")

  override def port = sys.env.get("EMAIL_PORT").map(_.toInt).getOrElse(465)

  override def ssl = sys.env.get("EMAIL_SSL").forall(_.toBoolean)
}