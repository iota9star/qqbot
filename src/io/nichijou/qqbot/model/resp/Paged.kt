package io.nichijou.qqbot.model.resp

data class Paged<T>(
  val page: Long,
  val limit: Int,
  val total: Long,
  val list: Collection<T>
) {
  companion object {
    fun <T> wrap(page: Long, limit: Int, total: Long, list: Collection<T>): Paged<T> {
      return Paged(page, limit, total, list)
    }
  }
}
