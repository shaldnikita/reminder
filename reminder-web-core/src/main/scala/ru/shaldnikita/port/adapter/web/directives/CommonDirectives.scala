package ru.shaldnikita.users.port.adapter.web.directives

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import ru.shaldnikita.users.domain.validation.Validator
import ru.shaldnikita.users.domain.validation.Validator.ValidationResult
import ru.shaldnikita.users.port.adapter.web.directives.ValidationDirectives._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 08.07.2019
  */
trait CommonDirectives {

  /**
    * id - UUID
    */
  val getById = get & path(JavaUUID)

  /**
    * id - UUID
    */
  val deleteById = delete & path(JavaUUID)

  /**
    * @param um like {@code as[Entity]}
    * @param validate like {@code Validator.validate()}
    * @tparam T - any type that can be unmarshalled
    * @return
    */
  def postEntity[T](um: FromRequestUnmarshaller[T],
                    validate: T => ValidationResult[T]): Directive1[T] = {
    post & validatedEntity(um, validate)
  }

  /**
    * @param um like {@code as[Entity]}
    * @param validate like {@code Validator.validate()}
    * @tparam T - any type that can be unmarshalled
    * @return
    */
  def putEntity[T](um: FromRequestUnmarshaller[T],
                   validate: T => ValidationResult[T]): Directive1[T] = {
    put & validatedEntity(um, validate)
  }

  /**
    * @param um like {@code as[Entity]}
    * @param validator like {@code Validator}
    * @tparam T - any type that can be unmarshalled
    * @return
    */
  def postEntity[T](
      um: FromRequestUnmarshaller[T],
      validator: Validator[T] = Validator.empty): Directive1[T] = {
    post & validatedEntity(um, validator.validate)
  }

  /**
    * @param um like {@code as[Entity]}
    * @param validator like {@code Validator}
    * @tparam T - any type that can be unmarshalled
    * @return
    */
  def putEntity[T](um: FromRequestUnmarshaller[T],
                   validator: Validator[T] = Validator.empty): Directive1[T] = {
    put & validatedEntity(um, validator.validate)
  }
  /*
  /**
 * @param validate like {@code Validator.validate()}
 * @tparam T - any type extending {@code ru.DtoModel}
 * @return
 */
  def postEntity[T <: WebModel[_]](validate: T => ValidationResult[T])(
      implicit un: FromRequestUnmarshaller[T]): Directive1[T] = {
    post & validatedEntity(as[T], validate)
  }

  /**
 * @param validate like {@code Validator.validate()}
 * @tparam T - any type extending {@code ru.DtoModel}
 * @return
 */
  def postEntity[T <: DtoModel[_, _]](validate: T => ValidationResult[T])(
      implicit un: FromRequestUnmarshaller[T]): Directive1[T] = {
    post & validatedEntity(as[T], validate)
  }

  /**
 * @tparam T - any type extending {@code ValidatedModel}
 * @return
 */
  def putValidatedEntity[T <: ValidatedModel[T]](
      implicit un: FromRequestUnmarshaller[T]): Directive1[T] = {
    put & entity(as[T]).flatMap { t =>
      val validator = t.validator
      validatedEntity(as[T], validator.validate)
    }
  }

  /**
 * @tparam T - any type extending {@code ValidatedModel}
 * @return
 */
  def postValidatedEntity[T <: ValidatedModel[T]](
      implicit un: FromRequestUnmarshaller[T]): Directive1[T] = {
     post & entity(as[T]).flatMap { t =>
      val validator = t.validator
      validatedEntity(as[T], validator.validate)
    }
  }*/
}
