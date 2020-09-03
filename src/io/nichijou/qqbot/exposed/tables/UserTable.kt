package io.nichijou.qqbot.exposed.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

// 用户信息
object UserTable : LongIdTable("tb_user") {
  // 账号
  val account = varchar("account", 32).uniqueIndex("unique_account")

  // 密码
  val password = varchar("password", 512)

  // 盐
  val salt = varchar("salt", 36)

  // 用户状态
  val stat = integer("stat").default(1)

  // 创建时间
  val createAt = datetime("create_at")

  // 更新时间
  val updateAt = datetime("update_at").nullable()
}
