package ru.otus.otuskotlin.user

import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import io.ktor.utils.io.charsets.Charsets
import kotlinx.serialization.json.Json
import org.junit.BeforeClass
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ApplicationStubTest {
    @Test
    fun testRoot() {
        with(engine) {
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
        with(engine) {
            handleRequest(HttpMethod.Post, "/api/get") {
                val body = KmpUserGet(
                        userId = "some-id",
                        debug = KmpUserGet.Debug(
                                stub = KmpUserGet.StubCases.SUCCESS
                        )
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

    @Test
    fun indexUser() {
        with(engine) {
            handleRequest(HttpMethod.Post, "/api/index") {
                val body = KmpUserIndex(
                        debug = KmpUserIndex.Debug(
                                stub = KmpUserIndex.StubCases.SUCCESS
                        )
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
                        assertEquals("ivanov-id", res.data?.first()?.id)
                        assertEquals(2, res.data?.size)
                    }
        }
    }

    @Test
    fun createUser() {
        with(engine) {
            handleRequest(HttpMethod.Post, "/api/create") {
                val body = KmpUserCreate(
                        fname = "Semen",
                        debug = KmpUserCreate.Debug(
                                stub = KmpUserCreate.StubCases.SUCCESS
                        )
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
                        assertEquals("test-create-id", res.data?.id)
                    }
        }
    }

    @Test
    fun updateUser() {
        with(engine) {
            handleRequest(HttpMethod.Post, "/api/update") {
                val body = KmpUserUpdate(
                        id = "ivanov-id",
                        fname = "Semen",
                        mname = "Semenovich",
                        lname = "Semenov",
                        debug = KmpUserUpdate.Debug(
                                stub = KmpUserUpdate.StubCases.SUCCESS
                        )
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
                        assertEquals("ivanov-id", res.data?.id)
                    }
        }
    }

    @Test
    fun deleteUser() {
        with(engine) {
            handleRequest(HttpMethod.Post, "/api/delete") {
                val body = KmpUserDelete(
                        userId = "delete-id",
                        debug = KmpUserDelete.Debug(
                                stub = KmpUserDelete.StubCases.SUCCESS
                        )
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
                        assertEquals("delete-id", res.data?.id)
                    }
        }
    }

    companion object {
        @OptIn(KtorExperimentalAPI::class)
        private val engine = TestApplicationEngine(createTestEnvironment {
            config = HoconApplicationConfig(ConfigFactory.load("application.conf"))
        })

        @BeforeClass
        @JvmStatic fun setup(){
            engine.start(wait = false)
        }
    }
}
