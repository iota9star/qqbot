package io.nichijou.qqbot.model.resp

import io.nichijou.qqbot.consts.BizStatus

data class Resp<T>(
  val code: Int = BizStatus.OK.code,
  val msg: String? = null,
  val data: T? = null
)
