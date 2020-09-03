package io.nichijou.qqbot.model.resp

import io.nichijou.qqbot.consts.BizStatus


object R {
  fun ok(): Resp<Unit> = Resp()
  fun <T> ok(data: T?): Resp<T> = Resp(data = data)
  fun err(): Resp<Unit> = Resp(code = BizStatus.ERR.code)
  fun err(msg: String?): Resp<Unit> = Resp(code = BizStatus.ERR.code, msg = msg)
}
