package ru.shaldnikita.timer.notificators.phone.conf

import com.typesafe.config.Config

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.04.2019
  */
sealed trait PhoneConfiguration {
  def apiId: String
}

class SystemEnvironmentTwilioPhoneConfiguration(config: Config) extends PhoneConfiguration {
  override def apiId = config.getString("sms_api_id")

}
