package ru.otus.otuskotlin.user.transport.common

import ru.otus.otuskotlin.user.common.UserContext

interface IUserTransportNotify {
    suspend fun notify(context: UserContext): UserContext
}
