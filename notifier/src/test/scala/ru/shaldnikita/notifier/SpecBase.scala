package ru.shaldnikita.notifier

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class SpecBase extends FlatSpec with MockFactory with ScalaFutures with Matchers{

}
