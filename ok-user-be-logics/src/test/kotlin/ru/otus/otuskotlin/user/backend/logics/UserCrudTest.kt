package ru.otus.otuskotlin.user.backend.logics

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import kotlin.test.Test
import kotlin.test.assertEquals

internal class UserCrudTest {
    @Test
    fun crudCreateTest() {
        val crud = UserCrud()
        val context = UserContext(
                requestUser = UserModel(
                        fname = "Ivan",
                        mname = "Ivanovich",
                        lname = "Ivanov"
                )
        )
        runBlocking {
            crud.create(context)
        }

        assertEquals(UserContextStatus.SUCCESS, context.status)
        assertEquals("Ivan", context.responseUser.fname)
    }
}
