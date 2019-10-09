package ru.shaldnikita.users

import org.scalamock.handlers.CallHandler
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, Matchers, WordSpec}
import zio.{DefaultRuntime, ZIO}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 27.04.2019
  */
class ItSpecBase
    extends WordSpec
    with MockFactory
    with ScalaFutures
      with BeforeAndAfterAll
      with BeforeAndAfter
    with Matchers {

  val runtime = new DefaultRuntime {}

  implicit case class RichZioTask[R, E, A](val task: ZIO[R, E, A]){
    def zioValue(r: R): A = {
      runtime.unsafeRun(task.provide(r))
    }
  }

  implicit class RichZIOCallHandler[R, E, A](
                                              val handler: CallHandler[ZIO[R, E, A]]) {

    def returningZ(value: A): handler.Derived =
      handler.returning(ZIO.succeed(value))

    def throwingZ(e: E)(implicit ev: E <:< Throwable): handler.Derived =
      handler.returning(ZIO.fail(e))
  }
}
