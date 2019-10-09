package ru.shaldnikita.users.port.adapter.dao.user

import java.util.UUID

import ru.shaldnikita.DaoSpecBase
import ru.shaldnikita.users.DaoSpecBase
import ru.shaldnikita.users.domain.models.contacts.ContactType
import ru.shaldnikita.users.port.adapter.dao.Tables
import ru.shaldnikita.users.port.adapter.dao.contact.ContactSchema
import ru.shaldnikita.users.users.domain.models.contacts.ContactType.Telegram
import ru.shaldnikita.users.users.port.adapter.dao.Tables
import ru.shaldnikita.users.users.port.adapter.dao.contact.ContactSchema
import ru.shaldnikita.users.users.port.adapter.dao.user.{UserDaoImpl, UserSchema}

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
class UserDaoSpec extends DaoSpecBase {

  private val userDao = new UserDaoImpl()

  val userId = UUID.randomUUID().toString
  val user   = UserSchema(userId, "firstName", "secondName")
  "UserDao" should {

    "create user" in {
      userDao.create(user).zioValue(db)

      db.run(Tables.users.filter(_.userId === userId).result.headOption)
        .futureValue
        .get shouldBe user
    }

    "update user" in {
      val updatedUser = user.copy(firstName = "Vasya")

      db.run(Tables.users += user).futureValue

      userDao.update(updatedUser).zioValue(db)

      db.run(Tables.users.filter(_.userId === userId).result.headOption)
        .futureValue
        .get shouldBe updatedUser
    }

    "delete user and his contacts" in {
      val userContact = ContactSchema(
        UUID.randomUUID().toString,
        Telegram.id,
        "@loopa",
        userId
      )
      db.run(DBIO.seq(Tables.users += user, Tables.contacts += userContact))
        .futureValue
      userDao.deleteUserAndContacts(userId).zioValue(db)
      db.run(Tables.users.filter(_.userId === userId).result.headOption)
        .futureValue shouldBe None
      db.run(Tables.contacts.filter(_.userId === userId).result.headOption)
        .futureValue shouldBe None
    }

    "find one user" in {
      db.run(Tables.users += user).futureValue
      userDao.findOne(userId).zioValue(db).get shouldBe user
    }

    "find all users" in {
      val secondUser = user.copy(userId = UUID.randomUUID().toString)
      db.run(Tables.users ++= Seq(user, secondUser)).futureValue
      userDao.findAll().zioValue(db) shouldBe Seq(user, secondUser)
    }
  }

  after {
    db.run(Tables.users.delete).futureValue
    db.run(Tables.contacts.delete).futureValue
  }
}
