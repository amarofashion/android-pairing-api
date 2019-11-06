package com.amaro

import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/guideShop/list").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
