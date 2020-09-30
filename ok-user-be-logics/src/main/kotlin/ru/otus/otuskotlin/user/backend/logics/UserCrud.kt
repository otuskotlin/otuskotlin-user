package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.user.backend.common.UserContext

class UserCrud {
    private val createChain = UserCreateChain()

    suspend fun create(context: UserContext) = createChain.exec(context)
}
