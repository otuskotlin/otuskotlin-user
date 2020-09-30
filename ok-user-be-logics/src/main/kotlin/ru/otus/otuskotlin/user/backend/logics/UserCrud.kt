package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.user.backend.common.UserContext

class UserCrud {
    private val getChain = UserGetChain()
    private val indexChain = UserIndexChain()
    private val createChain = UserCreateChain()
    private val updateChain = UserUpdateChain()
    private val deleteChain = UserDeleteChain()

    suspend fun get(context: UserContext) = getChain.exec(context)
    suspend fun index(context: UserContext) = indexChain.exec(context)
    suspend fun create(context: UserContext) = createChain.exec(context)
    suspend fun update(context: UserContext) = updateChain.exec(context)
    suspend fun delete(context: UserContext) = deleteChain.exec(context)
}
