package ru.otus.otuskotlin.user.transport.common

import ru.otus.otuskotlin.user.common.UserContext

interface IUserTransportConsume<T> {
    suspend fun consume(message: T, context: UserContext): UserContext
}
