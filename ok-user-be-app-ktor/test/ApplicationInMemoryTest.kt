package ru.otus.otuskotlin.user

import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.utils.io.charsets.Charsets
import kotlinx.serialization.json.Json
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import kotlin.test.*

class ApplicationInMemoryTest {
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
            var id = ""
            handleRequest(HttpMethod.Post, "/api/create") {
                val body = KmpUserCreate(
                        fname = "Semen",
                )
                val bodyString = Json.encodeToString(KmpUserCreate.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                val jsonString = response.content ?: fail("Null response json")
                val res = Json.decodeFromString(KmpUserResponseItem.serializer(), jsonString)
                id = res.data?.id ?: fail("No id in response")
            }
            handleRequest(HttpMethod.Post, "/api/get") {
                val body = KmpUserGet(
                        userId = id,
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
                        assertEquals(id, res.data?.id)
                        assertEquals("Semen", res.data?.fname)
                    }
        }
    }

    @Test
    fun indexUser() {
        withTestApplication({ module(testing = true) }) {
            var id = ""
            handleRequest(HttpMethod.Post, "/api/create") {
                val body = KmpUserCreate(
                        fname = "Semen",
                        dob = "2000-01-02"
                )
                val bodyString = Json.encodeToString(KmpUserCreate.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                val jsonString = response.content ?: fail("Null response json")
                val res = Json.decodeFromString(KmpUserResponseItem.serializer(), jsonString)
                id = res.data?.id ?: fail("No id in response")
            }
            handleRequest(HttpMethod.Post, "/api/index") {
                val body = KmpUserIndex(
                        filter = KmpUserIndex.Filter(dob = "2000-01-02")
                )
                val bodyString = Json.encodeToString(KmpUserIndex.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }
                    .apply {
                        assertEquals(HttpStatusCode.OK, response.status())
                        assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                        val jsonString = response.content ?: fail("Null response json")
                        val res = Json.decodeFromString(KmpUserResponseIndex.serializer(), jsonString)
                        assertEquals(id, res.data?.first()?.id)
                        assertEquals(1, res.data?.size)
                    }
        }
    }

    @Test
    fun createUser() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/api/create") {
                val body = KmpUserCreate(
                        fname = "Semen",
                )
                val bodyString = Json.encodeToString(KmpUserCreate.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }
                    .apply {
                        assertEquals(HttpStatusCode.OK, response.status())
                        assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                        val jsonString = response.content ?: fail("Null response json")
                        val res = Json.decodeFromString(KmpUserResponseItem.serializer(), jsonString)
                        assertNotNull(res.data?.id)
                    }
        }
    }

    @Test
    fun updateUser() {
        withTestApplication({ module(testing = true) }) {
            var id = ""
            handleRequest(HttpMethod.Post, "/api/create") {
                val body = KmpUserCreate(
                        fname = "Semen",
                )
                val bodyString = Json.encodeToString(KmpUserCreate.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                val jsonString = response.content ?: fail("Null response json")
                val res = Json.decodeFromString(KmpUserResponseItem.serializer(), jsonString)
                id = res.data?.id ?: fail("No id in response")
            }
            handleRequest(HttpMethod.Post, "/api/update") {
                val body = KmpUserUpdate(
                        id = id,
                        fname = "Semen",
                        mname = "Semenovich",
                        lname = "Semenov",
                )
                val bodyString = Json.encodeToString(KmpUserUpdate.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }
                    .apply {
                        assertEquals(HttpStatusCode.OK, response.status())
                        assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                        val jsonString = response.content ?: fail("Null response json")
                        val res = Json.decodeFromString(KmpUserResponseItem.serializer(), jsonString)
                        assertEquals(id, res.data?.id)
                        assertEquals("Semen", res.data?.fname)
                        assertEquals("Semenov", res.data?.lname)
                    }
        }
    }

    @Test
    fun deleteUser() {
        withTestApplication({ module(testing = true) }) {
            var id = ""
            handleRequest(HttpMethod.Post, "/api/create") {
                val body = KmpUserCreate(
                        fname = "Semen",
                )
                val bodyString = Json.encodeToString(KmpUserCreate.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                val jsonString = response.content ?: fail("Null response json")
                val res = Json.decodeFromString(KmpUserResponseItem.serializer(), jsonString)
                id = res.data?.id ?: fail("No id in response")
            }
            handleRequest(HttpMethod.Post, "/api/delete") {
                val body = KmpUserDelete(
                        userId = id,
                )
                val bodyString = Json.encodeToString(KmpUserDelete.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }
                    .apply {
                        assertEquals(HttpStatusCode.OK, response.status())
                        assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                        val jsonString = response.content ?: fail("Null response json")
                        val res = Json.decodeFromString(KmpUserResponseItem.serializer(), jsonString)
                        assertEquals(id, res.data?.id)
                    }
        }
    }
}
