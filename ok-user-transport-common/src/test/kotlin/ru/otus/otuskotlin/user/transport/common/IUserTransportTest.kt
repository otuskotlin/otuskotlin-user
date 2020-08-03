package ru.otus.otuskotlin.user.transport.common

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.user.common.UserContext
import ru.otus.otuskotlin.user.common.models.UserModel
import kotlin.test.Test
import kotlin.test.assertEquals

internal class IUserTransportTest {

    @Test
    fun consumeTest() {
        val context = UserContext()
        val transportService = UserTransportMq()
        val resContext = runBlocking {
            transportService.consumeGet(UserMqGet("test"), context)
        }
        assertEquals(resContext.queryUserId, "test")
    }

    @Test
    fun notifyTest() {
        val context = UserContext(responseUser = UserModel(id = "123"))
        val transportService = UserTransportMq()
        runBlocking {
            transportService.notifyUpdated(context)
        }
    }

    class UserTransportMq : IUserTransport<UserMqGet, UserMqList, UserMqNone, UserMqNone, UserMqNone> {

        private val consumeGet = ConsumeGet()
        private val consumeIndex = ConsumeIndex()
        private val consumeNone = ConsumeNone()
        private val notifyUpdated = NotifyUpdated()

        override suspend fun consumeGet(message: UserMqGet, context: UserContext): UserContext = consumeGet.consume(message, context)

        override suspend fun consumeIndex(message: UserMqList, context: UserContext): UserContext = consumeIndex.consume(message, context)

        override suspend fun consumeCreate(message: UserMqNone, context: UserContext): UserContext = consumeNone.consume(message, context)

        override suspend fun consumeUpdate(message: UserMqNone, context: UserContext): UserContext = consumeNone.consume(message, context)

        override suspend fun consumeRemove(message: UserMqNone, context: UserContext): UserContext = consumeNone.consume(message, context)

        override suspend fun notifyUpdated(context: UserContext): UserContext = notifyUpdated.notify(context)
    }

    class NotifyUpdated: IUserTransportNotify {
        override suspend fun notify(context: UserContext): UserContext = context.also {
            println(it.responseUser.toString())
        }

    }

    class ConsumeGet : IUserTransportConsume<UserMqGet> {
        override suspend fun consume(message: UserMqGet, context: UserContext): UserContext = context.apply {
            queryUserId = message.id ?: ""
        }
    }

    class ConsumeIndex : IUserTransportConsume<UserMqList> {
        override suspend fun consume(message: UserMqList, context: UserContext): UserContext = context
    }

    class ConsumeNone : IUserTransportConsume<UserMqNone> {
        override suspend fun consume(message: UserMqNone, context: UserContext): UserContext = context
    }

    data class UserMqGet(
            val id: String? = null
    )

    class UserMqList()
    class UserMqNone()

}
