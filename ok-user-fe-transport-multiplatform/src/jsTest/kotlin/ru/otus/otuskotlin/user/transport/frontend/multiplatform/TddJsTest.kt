package ru.otus.otuskotlin.user.transport.frontend.multiplatform

import kotlinx.coroutines.await
import ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserCreate
import kotlin.js.Promise
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TddJsTest {
    @Ignore
    @Test
    fun queryTest(): Promise<Unit> {
        val backendService = KmpUserBackendServiceJS(endpoint = "http://localhost:8080/user")
        return backendService.create(KmpUserCreate(
                    fname = "First",
                    mname = "Middle",
                    lname = "Last"
            ))
                .then {
                    assertEquals("First", it.data?.fname)
                }
    }
}
