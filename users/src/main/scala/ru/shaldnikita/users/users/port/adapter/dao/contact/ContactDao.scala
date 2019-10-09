package ru.shaldnikita.users.users.port.adapter.dao.contact

import cats.data.NonEmptyList
import ru.shaldnikita.users.DBTask

trait ContactDao {
  def create(contact: ContactSchema): DBTask[Option[Int]]
  def create(contacts: NonEmptyList[ContactSchema]): DBTask[Option[Int]]
  def update(contact: ContactSchema): DBTask[Int]
  def delete(contactId: String): DBTask[Int]
  def findUserContacts(userId: String): DBTask[Seq[ContactSchema]]
  def updateAllUserContacts(userId: String,
                            contacts: NonEmptyList[ContactSchema]): DBTask[Int]
  def removeAllUserContacts(userId: String): DBTask[Int]
}
