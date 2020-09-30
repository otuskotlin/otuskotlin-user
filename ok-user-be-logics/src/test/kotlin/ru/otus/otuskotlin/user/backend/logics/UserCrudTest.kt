package ru.otus.otuskotlin.user.backend.logics

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import kotlin.test.Test
import kotlin.test.assertEquals

internal class UserCrudTest {
    @Test
    fun crudGetTest() {
        val crud = UserCrud()
        val context = UserContext(
                requestUserId = "get-id"
        )
        runBlocking {
            crud.get(context)
        }

        assertEquals(UserContextStatus.SUCCESS, context.status)
        assertEquals("get-id", context.responseUser.id)
        assertEquals("Ivan", context.responseUser.fname)
    }

    @Test
    fun crudIndexTest() {
        val crud = UserCrud()
        val context = UserContext(
        )
        runBlocking {
            crud.index(context)
        }

        assertEquals(UserContextStatus.SUCCESS, context.status)
        assertEquals(2, context.responseUsers.size)
        assertEquals("ivanov-id", context.responseUsers.first().id)
        assertEquals("Ivan", context.responseUsers.first().fname)
        assertEquals("Petr", context.responseUsers.last().fname)
    }

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

    @Test
    fun crudUpdateTest() {
        val crud = UserCrud()
        val context = UserContext(
                requestUser = UserModel(
                        id = "ivanov-id",
                        fname = "Ivan",
                        mname = "Ivanovich",
                        lname = "Ivanov"
                )
        )
        runBlocking {
            crud.update(context)
        }

        assertEquals(UserContextStatus.SUCCESS, context.status)
        assertEquals("ivanov-id", context.responseUser.id)
        assertEquals("Ivan", context.responseUser.fname)
    }

    @Test
    fun crudDeleteTest() {
        val crud = UserCrud()
        val context = UserContext(
                requestUserId = "ivanov-id"
        )
        runBlocking {
            crud.delete(context)
        }

        assertEquals(UserContextStatus.SUCCESS, context.status)
        assertEquals("ivanov-id", context.responseUser.id)
        assertEquals("Ivan", context.responseUser.fname)
    }
}
