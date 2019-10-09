package ru.shaldnikita.users

import org.scalamock.handlers.CallHandler
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import zio.ZIO

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class SpecBase
    extends WordSpec
    with MockFactory
    with ScalaFutures
    with Matchers
    with ScalamockRichCallHandlers {

  implicit class RichZIOCallHandler[R, E, A](
      val handler: CallHandler[ZIO[R, E, A]]) {

    def returningZ(value: A): handler.Derived =
      handler.returning(ZIO.succeed(value))

    def throwingZ(e: E)(implicit ev: E <:< Throwable): handler.Derived =
      handler.returning(ZIO.fail(e))
  }
}
