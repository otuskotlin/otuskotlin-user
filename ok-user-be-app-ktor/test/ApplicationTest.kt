package ru.otus.otuskotlin.user

import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.utils.io.charsets.Charsets
import kotlinx.serialization.json.Json
import ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserGet
import ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserResponseItem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertTrue {
                    response.content?.contains("Hello World!") ?: false
                }
            }
        }
    }

    @Test
    fun getUser() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/api/get") {
                val body = KmpUserGet(
                        userId = "some-id"
                )
                val bodyString = Json.encodeToString(KmpUserGet.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }
                    .apply {
                        assertEquals(HttpStatusCode.OK, response.status())
                        assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                        val jsonString = response.content ?: fail("Null response json")
                        val res = Json.decodeFromString(KmpUserResponseItem.serializer(), jsonString)
                        assertEquals("some-id", res.data?.id)
                    }
        }
    }
}
