package io.nichijou.qqbot.model.resp

data class QQResp(
  val id: Long,
  val nickname: String,
  // 是否在线
  val online: Boolean,
  //好友数
  val friends: Int,
  // QQ群数量
  val groups: Int
)
