package ru.otus.otuskotlin.user

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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

//    @Test
//    fun getUser() {
//        withTestApplication({ module(testing = true) }) {
//            handleRequest(HttpMethod.Post, "/api/get") {
//                val body = KmpUserGet(
//                        userId = "some-id"
//                )
//                val bodyString = Json.encodeToString(KmpUserGet.serializer(), body)
//                setBody(bodyString)
//                header("Content-Type: application/json")
//            }
//                    .apply {
//                        assertEquals(HttpStatusCode.OK, response.status())
//                        assertTrue {
//                            response.content?.contains("Hello World!") ?: false
//                        }
//                    }
//        }
//    }
}
