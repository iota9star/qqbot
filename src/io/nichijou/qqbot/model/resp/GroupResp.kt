package io.nichijou.qqbot.model.resp

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.PermissionDeniedException
import net.mamoe.mirai.event.events.GroupNameChangeEvent

data class GroupResp(
  /**
   * 同为 groupCode, 用户看到的群号码.
   */
  val id: Long,

  /**
   * 群名称.
   *
   * 在修改时将会异步上传至服务器, 也会广播事件 [GroupNameChangeEvent].
   * 频繁修改可能会被服务器拒绝.
   *
   * @see GroupNameChangeEvent 群名片修改事件
   * @throws PermissionDeniedException 无权限修改时将会抛出异常
   */
  var name: String,

  /**
   * 群头像下载链接.
   */
  val avatar: String,

  /**
   * 群成员列表, 不含机器人自己, 含群主.
   * 在 [Group] 实例创建的时候查询一次. 并与事件同步事件更新
   */
  val members: Int
)
