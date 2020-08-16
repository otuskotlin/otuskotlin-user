package ru.otus.otuskotlin.user.transport.frontend.multiplatform

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserCreate
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TddJvmTest {
    @Ignore
    @Test
    fun queryTest() {
        val backendService = KmpUserBackendService(endpoint = "http://localhost:8080/user")
        val result = runBlocking {
            backendService.create(KmpUserCreate(
                    fname = "First",
                    mname = "Middle",
                    lname = "Last"
            ))
        }
        assertEquals("First", result.data?.fname)
    }
}
