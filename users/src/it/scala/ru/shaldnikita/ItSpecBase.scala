package ru.shaldnikita

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class ItSpecBase
    extends WordSpec
    with MockFactory
    with ScalaFutures
    with Matchers
