package ru.otus.otuskotlin.user.transport.multiplatform.backend

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class KmpUserBackendTest {

    val processor = Processor()
    val messageGet = KmpUserGet(userId = "test-id")
    val messageIndex = KmpUserIndex()
    val messageCreate = KmpUserCreate(fname = "John")
    val messageUpdate = KmpUserUpdate(fname = "Peter")
    val messageRemove = KmpUserDelete(userId = "test-id")

    /**
     * Полагаем, что мы в методе контроллера
     */
    @Test
    fun `call get in controller`() {
        val context = UserContext()
        val result = runBlocking {
            processor.get(context.setQuery(messageGet)).resultItem()
        }
        assertEquals("test-id", result.data?.id)
    }

    /**
     * Полагаем, что мы в методе контроллера
     */
    @Test
    fun `call create in controller`() {
        val context = UserContext()
        val result = runBlocking {
            processor.create(context.setQuery(messageCreate)).resultItem()
        }
        assertEquals("test-id", result.data?.id)
        assertEquals("John", result.data?.fname)
    }

    /**
     * Полагаем, что мы в методе контроллера
     */
    @Test
    fun `call update in controller`() {
        val context = UserContext()
        val result = runBlocking {
            processor.update(context.setQuery(messageUpdate)).resultItem()
        }
        assertEquals("Peter", result.data?.fname)
    }

    /**
     * Полагаем, что мы в методе контроллера
     */
    @Test
    fun `call remove in controller`() {
        val context = UserContext()
        val result = runBlocking {
            processor.remove(context.setQuery(messageRemove)).resultItem()
        }
        assertEquals("test-id", result.data?.id)
    }

    /**
     * Класс процессора моделирует модуль бизнес-логики
     */
    class Processor() {
        suspend fun get(context: UserContext): UserContext = context.apply { responseUser = UserModel("test-id", fname = "Ivan") }
        suspend fun index(context: UserContext): UserContext = context.apply {  }
        suspend fun create(context: UserContext): UserContext = context.apply { responseUser = requestUser.copy(id = "test-id") }
        suspend fun update(context: UserContext): UserContext = context.apply { responseUser = requestUser.copy() }
        suspend fun remove(context: UserContext): UserContext = context.apply { responseUser = UserModel(id = requestUserId) }
    }
}
