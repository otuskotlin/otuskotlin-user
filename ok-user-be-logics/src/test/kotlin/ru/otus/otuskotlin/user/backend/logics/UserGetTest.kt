package ru.otus.otuskotlin.user.backend.logics

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserGetStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import kotlin.test.Test
import kotlin.test.assertEquals

internal class UserGetTest {
    @Test
    fun crudGetTest() {
        val crud = UserCrud()
        val context = UserContext(
                requestUserId = "get-id",
                stubGetCase = UserGetStubCases.SUCCESS
        )
        runBlocking {
            crud.get(context)
        }

        assertEquals(UserContextStatus.SUCCESS, context.status)
        assertEquals("Ivan", context.responseUser.fname)
        assertEquals("get-id", context.responseUser.id)
    }
}
