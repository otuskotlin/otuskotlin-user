package ru.otus.otuskotlin.user.backend.logics

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.backend.repository.inmemory.UserInMemoryDto
import ru.otus.otuskotlin.backend.repository.inmemory.UserRepositoryInMemoty
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserIndexFilter
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

internal class UserCrudInMemoryTest {
    @OptIn(ExperimentalTime::class)
    private val userRepo = UserRepositoryInMemoty(
            ttl = 20.toDuration(DurationUnit.SECONDS),
            listOf(
                    UserModel(id = "get-id", fname = "Ivan"),
                    UserModel(id = "update-id"),
                    UserModel(id = "delete-id", fname = "Ivan"),
                    UserModel(id = "index-id-1", dob = LocalDate.parse("2020-01-01"), fname = "Ivan"),
                    UserModel(id = "index-id-2", dob = LocalDate.parse("2020-01-01"), fname = "Petr"),
                    UserModel(id = "index-id-3", dob = LocalDate.parse("2020-01-01"), fname = "John"),
            )
    )

    @Test
    fun crudGetTest() {
        val crud = UserCrud(userRepo)
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
        val crud = UserCrud(userRepo)
        val context = UserContext(
                requestUserFilter = UserIndexFilter(dob = "2020-01-01")
        )
        runBlocking {
            crud.index(context)
        }

        assertEquals(UserContextStatus.SUCCESS, context.status)
        assertEquals(3, context.responseUsers.size)
        assertEquals("index-id-1", context.responseUsers.first().id)
        assertEquals("Ivan", context.responseUsers.first().fname)
        assertEquals("John", context.responseUsers.last().fname)
    }

    @Test
    fun crudCreateTest() {
        val crud = UserCrud(userRepo)
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
        val crud = UserCrud(userRepo)
        val context = UserContext(
                requestUser = UserModel(
                        id = "update-id",
                        fname = "Ivan",
                        mname = "Ivanovich",
                        lname = "Ivanov"
                )
        )
        runBlocking {
            crud.update(context)
        }

        assertEquals(UserContextStatus.SUCCESS, context.status)
        assertEquals("update-id", context.responseUser.id)
        assertEquals("Ivan", context.responseUser.fname)
    }

    @Test
    fun crudDeleteTest() {
        val crud = UserCrud(userRepo)
        val context = UserContext(
                requestUserId = "delete-id"
        )
        runBlocking {
            crud.delete(context)
        }

        assertEquals(UserContextStatus.SUCCESS, context.status)
        assertEquals("delete-id", context.responseUser.id)
        assertEquals("Ivan", context.responseUser.fname)
    }
}
