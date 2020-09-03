package io.nichijou.qqbot.exposed

import io.nichijou.qqbot.exposed.tables.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

// 用户信息
class User(id: EntityID<Long>) : LongEntity(id) {

  companion object : LongEntityClass<User>(UserTable)

  // 账号
  val account by UserTable.account

  // 密码
  val password by UserTable.password

  // 盐
  val salt by UserTable.salt

  // 用户状态
  val stat by UserTable.stat

  // 创建时间
  val createAt by UserTable.createAt

  // 更新时间
  val updateAt by UserTable.updateAt
}
