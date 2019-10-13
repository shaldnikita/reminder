package ru.shaldnikita.users.users.port.adapter.dao.mappers
import ru.shaldnikita.users.users.domain.models.contacts.Contact.ContactId
import ru.shaldnikita.users.users.domain.models.users.User.UserId
import slick.jdbc.PostgresProfile.api._

trait EntityIdMappers {

  implicit def userIdMapper: BaseColumnType[UserId] =
    MappedColumnType.base[UserId, String](
      ent => ent.id,
      value => UserId(value)
    )

  implicit def contactIdMapper: BaseColumnType[ContactId] =
    MappedColumnType.base[ContactId, String](
      ent => ent.id,
      value => ContactId(value)
    )
}
