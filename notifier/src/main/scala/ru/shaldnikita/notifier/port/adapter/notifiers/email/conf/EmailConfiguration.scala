package ru.shaldnikita.notifier.port.adapter.notifiers.email.conf

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

  def subject: String
}

class SystemEnvironmentEmailConfiguration(config: Config) extends EmailConfiguration {
  override def fromEmail = config.getString("fromEmail")

  override def fromName = config.getString("fromName")

  override def subject = config.getString("subject")

  override def login = config.getString("login")

  override def password = config.getString("password")

  override def hostName = config.getString("settings.hostName")

  override def port = config.getInt("settings.smtpPort")

  override def ssl = config.getBoolean("settings.sslConnect")
}