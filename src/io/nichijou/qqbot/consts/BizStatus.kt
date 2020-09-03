package io.nichijou.qqbot.consts

data class BizStatus(val code: Int) {
  companion object {
    val OK = BizStatus(1)
    val ERR = BizStatus(0)
  }
}
