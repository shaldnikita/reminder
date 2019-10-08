package ru.shaldnikita.users.port.adapter.web.handlers.users

import java.util.UUID

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import ru.shaldnikita.HandlerSpecBase
import ru.shaldnikita.users.application.UserService
import ru.shaldnikita.users.application.exceptions.UserNotFoundException
import ru.shaldnikita.users.domain.models.users.User
import ru.shaldnikita.users.port.adapter.web.models.users.{CreateUserModel, UserModel}
import ru.shaldnikita.users.port.adapter.web.handlers.users.UserHandler
import ru.shaldnikita.users.port.adapter.web.models.users.{CreateUserModel, UserModel}
import ru.shaldnikita.users.application.UserService
import ru.shaldnikita.users.application.exceptions.UserNotFoundException
import ru.shaldnikita.users.application.models.users.CreateUser
import ru.shaldnikita.users.domain.models.users.User

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
class UserHandlerSpec extends HandlerSpecBase {

  private val userService = mock[UserService]
  private val userHandler = new UserHandler(userService)
  private val router      = userHandler.route

  "UserHandler" should {
    val userId = UUID.randomUUID().toString

    val user      = User(userId, "firstName", "secondName", Nil)
    val userModel = UserModel(userId, "firstName", "secondName", Nil)

    val createUser      = CreateUser("firstName", "secondName", Nil)
    val createUserModel = CreateUserModel("firstName", "secondName", Nil)

    "create user" in {
      (userService.createUser _)
        .expects(createUser)
        .returningF(user)

      Post("/users", createUserModel) ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.Created
        responseAs[UserModel] shouldBe userModel
      }
    }

    "return 400 if validation failed on create" in {
      (userService.createUser _)
        .expects(*)
        .never()

      Post("/users", createUserModel.copy(firstName = "")) ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.BadRequest
      }
    }

    "update user" in {
      val updatedUser      = user.copy(firstName = "test")
      val updatedUserModel = userModel.copy(firstName = "test")

      (userService.updateUser _)
        .expects(updatedUser)
        .returningF(updatedUser)

      Put("/users", updatedUserModel) ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.OK
        responseAs[UserModel] shouldBe updatedUserModel
      }
    }

    "return 400 if validation failed on update" in {
      (userService.updateUser _)
        .expects(*)
        .never()

      Put("/users", userModel.copy(userId = "asd")) ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.BadRequest
      }
    }

    "return 404 if no user to be updated" in {
      (userService.updateUser _)
        .expects(user)
        .throwingF(UserNotFoundException(userId))

      Put("/users", userModel) ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.NotFound
      }
    }

    "delete user" in {
      (userService.deleteUser _)
        .expects(userId)
        .returningF(())

      Delete(s"/users/$userId") ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "return 404 if no user to be deleted" in {
      (userService.deleteUser _)
        .expects(userId)
        .throwingF(UserNotFoundException(userId))

      Delete(s"/users/$userId") ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.NotFound
      }
    }

    "find one user" in {
      (userService.findUser _)
        .expects(userId)
        .returningF(user)

      Get(s"/users/$userId") ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.OK
        responseAs[UserModel] shouldBe userModel
      }
    }

    "return 404 if no user found" in {
      (userService.findUser _)
        .expects(userId)
        .throwingF(UserNotFoundException(userId))

      Get(s"/users/$userId") ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.NotFound
      }
    }

    "find all users" in {
      (userService.findUsers _)
        .expects()
        .returningF(Seq(user))

      Get("/users") ~> Route.seal(router) ~> check {
        status shouldBe StatusCodes.OK
        responseAs[Seq[UserModel]] shouldBe Seq(userModel)
      }
    }

  }
}
