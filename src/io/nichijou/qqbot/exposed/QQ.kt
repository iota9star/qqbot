package io.nichijou.qqbot.exposed

import io.nichijou.qqbot.exposed.tables.QQTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// 认证信息
class QQ(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<QQ>(QQTable)

  // 密码
  var password by QQTable.password

  // 备注
  var notes by QQTable.notes
}
