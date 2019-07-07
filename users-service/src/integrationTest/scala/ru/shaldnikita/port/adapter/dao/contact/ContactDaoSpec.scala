package ru.shaldnikita.port.adapter.dao.contact

import java.util.UUID

import cats.data.NonEmptyList
import ru.shaldnikita.DaoSpecBase
import ru.shaldnikita.domain.models.contacts.ContactType
import ru.shaldnikita.port.adapter.dao.Tables
import ru.shaldnikita.port.adapter.dao.user.UserSchema

import scala.concurrent.ExecutionContext.Implicits._

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 07.07.2019
  */
class ContactDaoSpec extends DaoSpecBase {

  val userId = UUID.randomUUID().toString
  val user = UserSchema(
    userId,
    "firstName",
    "secondName"
  )
  val contactId = UUID.randomUUID().toString
  val contact = ContactSchema(
    contactId,
    ContactType.Telegram.id,
    "@loopa",
    userId
  )
  private val contactDao = new ContactDao

  "ContactDao" should {
    "create contact" in {
      contactDao.create(contact).futureValue

      findContact.get shouldBe contact
    }

    "update contact" in {
      val updatedContact = contact.copy(value = "@poopa")
      createContact()

      contactDao.update(updatedContact).futureValue

      findContact.get shouldBe updatedContact
    }

    "delete contact" in {
      createContact()
      contactDao.delete(contact.contactId).futureValue
      findContact shouldBe None
    }

    "find user contacts" in {
      val secondContact = contact.copy(contactId = UUID.randomUUID().toString)
      createContact()
      createContact(secondContact)

      contactDao.findUserContacts(userId).futureValue shouldBe Seq(
        contact,
        secondContact)
    }

    "update all user contacts" in {
      val secondContact = contact.copy(contactId = UUID.randomUUID().toString)
      createContact()
      createContact(secondContact)

      val updatedContact0 = contact.copy(value = "@Vasya")
      val updatedContact1 = secondContact.copy(value = "@Maxim")

      contactDao
        .updateAllUserContacts(
          userId,
          NonEmptyList.of(updatedContact0, updatedContact1))
        .futureValue

      db.run(Tables.contacts.filter(_.userId === userId).result)
        .futureValue shouldBe Seq(updatedContact0, updatedContact1)
    }

  }

  private def createContact(contact: ContactSchema = contact): Unit = {
    db.run(Tables.contacts += contact).futureValue
  }

  private def findContact: Option[ContactSchema] = {
    db.run(
        Tables.contacts
          .filter(_.contactId === contactId)
          .result
          .headOption)
      .futureValue
  }

  after {
    db.run(Tables.users.delete).futureValue
    db.run(Tables.contacts.delete).futureValue
  }
}
