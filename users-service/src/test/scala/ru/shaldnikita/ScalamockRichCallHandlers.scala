package ru.shaldnikita

import org.scalamock.handlers.CallHandler

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
trait ScalamockRichCallHandlers {

  implicit class RichFutureCallHandler[R](val handler: CallHandler[Future[R]]) {

    def returningF(value: R): handler.Derived =
      handler.returning(Future.successful(value))
    def throwingF(e: Throwable): handler.Derived =
      handler.returning(Future.failed(e))
  }

  implicit class RichTryCallHandler[R](val handler: CallHandler[Try[R]]) {

    def returningT(value: R): handler.Derived =
      handler.returning(Success(value))

    def throwingT(e: Throwable): handler.Derived =
      handler.returning(Failure(e))
  }
}
