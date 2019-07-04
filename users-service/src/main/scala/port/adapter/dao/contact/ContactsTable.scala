package port.adapter.dao.contact

import slick.jdbc.H2Profile.api._
import slick.lifted.Tag

/**
  * @author Nikita Shaldenkov <shaldnikita2@yandex.ru>
  *         on 04.07.2019
  */

class ContactsTable(tag: Tag) extends Table[ContactSchema](tag, "contacts") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  override def * = (contactId, `type`, value, userId) <> (ContactSchema.tupled, ContactSchema.unapply)

  def contactId = column[String]("contact_id", O.Unique)

  def `type` = column[String]("type")

  def value = column[String]("value")

  def userId = column[String]("user_id")

  def contact_id_udx = index("contacts_id_udx", contactId, unique = true)

  def contact_user_id_idx = index("contacts_user_id_udx", userId)
}

case class ContactSchema(contactId: String,
                         `type`: String,
                         value: String,
                         userId: String)