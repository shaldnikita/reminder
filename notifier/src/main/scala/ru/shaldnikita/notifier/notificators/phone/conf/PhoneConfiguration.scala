package ru.shaldnikita.notifier.notificators.phone.conf

import com.typesafe.config.Config

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.04.2019
  */
sealed trait PhoneConfiguration {
  def apiId: String
}

class SystemEnvironmentPhoneConfiguration(config: Config) extends PhoneConfiguration {
  override def apiId: String = config.getString("api_id")
}
