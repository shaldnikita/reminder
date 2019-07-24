package ru.shaldnikita.port.adapter.dao.user

import java.util.UUID

import ru.shaldnikita.DaoSpecBase
import ru.shaldnikita.domain.models.contacts.ContactType
import ru.shaldnikita.port.adapter.dao.Tables
import ru.shaldnikita.port.adapter.dao.contact.ContactSchema

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
class UserDaoSpec extends DaoSpecBase {

  private val userDao = new UserDao()

  val userId = UUID.randomUUID().toString
  val user   = UserSchema(userId, "firstName", "secondName")
  "UserDao" should {

    "create user" in {
      userDao.create(user).futureValue

      db.run(Tables.users.filter(_.userId === userId).result.headOption)
        .futureValue
        .get shouldBe user
    }

    "update user" in {
      val updatedUser = user.copy(firstName = "Vasya")

      db.run(Tables.users += user).futureValue

      userDao.update(updatedUser).futureValue

      db.run(Tables.users.filter(_.userId === userId).result.headOption)
        .futureValue
        .get shouldBe updatedUser
    }

    "delete user and his contacts" in {
      val userContact = ContactSchema(
        UUID.randomUUID().toString,
        ContactType.Telegram.id,
        "@loopa",
        userId
      )
      db.run(DBIO.seq(Tables.users += user, Tables.contacts += userContact))
        .futureValue
      userDao.deleteUserAndContacts(userId).futureValue
      db.run(Tables.users.filter(_.userId === userId).result.headOption)
        .futureValue shouldBe None
      db.run(Tables.contacts.filter(_.userId === userId).result.headOption)
        .futureValue shouldBe None
    }

    "find one user" in {
      db.run(Tables.users += user).futureValue
      userDao.findOne(userId).futureValue.get shouldBe user
    }

    "find all users" in {
      val secondUser = user.copy(userId = UUID.randomUUID().toString)
      db.run(Tables.users ++= Seq(user, secondUser)).futureValue
      userDao.findAll().futureValue shouldBe Seq(user, secondUser)
    }
  }

  after {
    db.run(Tables.users.delete).futureValue
    db.run(Tables.contacts.delete).futureValue
  }
}
