package ru.shaldnikita.users.users.port.adapter.dao.contact

import ru.shaldnikita.users.domain.models.contacts.ContactType
import ru.shaldnikita.users.domain.models.contacts.{Contact, ContactType}
import ru.shaldnikita.users.domain.models.contacts.ContactType
import ru.shaldnikita.users.users.domain.models.contacts.{Contact, ContactType}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */
class ContactsTable(tag: Tag) extends Table[ContactSchema](tag, "contacts") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  override def * =
    (contactId, `type`, value, userId) <> (ContactSchema.tupled, ContactSchema.unapply)

  def `type` = column[String]("type")

  def value = column[String]("value")

  def contact_id_udx = index("contacts_id_udx", contactId, unique = true)

  def contactId = column[String]("contact_id", O.Unique)

  def contact_user_id_idx = index("contacts_user_id_udx", userId)

  def userId = column[String]("user_id")
}

case class ContactSchema(contactId: String,
                         `type`: String,
                         value: String,
                         userId: String) {
  def toDomain = {
    Contact(
      contactId,
      //todo add UNDEFINED type and log error
      ContactType
        .ofId(`type`)
        .getOrElse(
          throw new Error(
            s"Invalid contact type [${`type`}] for record [$contactId] found in database."
          )
        ),
      value,
      userId
    )
  }
}
