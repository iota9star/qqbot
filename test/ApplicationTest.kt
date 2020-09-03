package io.nichijou

import io.ktor.http.*
import io.ktor.server.testing.*
import io.nichijou.qqbot.module
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
  @Test
  fun testRoot() {
    withTestApplication({ module(testing = true) }) {
      handleRequest(HttpMethod.Get, "/").apply {
        assertEquals(HttpStatusCode.OK, response.status())
        assertEquals("HELLO WORLD!", response.content)
      }
    }
  }
}
