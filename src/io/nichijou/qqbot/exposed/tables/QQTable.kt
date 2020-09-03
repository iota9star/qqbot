package io.nichijou.qqbot.exposed.tables

import org.jetbrains.exposed.dao.id.LongIdTable

// qq帐号
object QQTable : LongIdTable("tb_qq") {
  // 密码
  val password = varchar("password", 128)

  // 备注
  val notes = varchar("notes", 255).nullable()
}
