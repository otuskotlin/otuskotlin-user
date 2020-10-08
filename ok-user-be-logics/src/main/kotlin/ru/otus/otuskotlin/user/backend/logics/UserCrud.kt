package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository

class UserCrud(userRepo: IUserRepository = IUserRepository.NONE) {
    private val getChain = UserGetChain(userRepo = userRepo)
    private val indexChain = UserIndexChain(userRepo = userRepo)
    private val createChain = UserCreateChain(userRepo = userRepo)
    private val updateChain = UserUpdateChain(userRepo = userRepo)
    private val deleteChain = UserDeleteChain(userRepo = userRepo)

    suspend fun get(context: UserContext) = getChain.exec(context)
    suspend fun index(context: UserContext) = indexChain.exec(context)
    suspend fun create(context: UserContext) = createChain.exec(context)
    suspend fun update(context: UserContext) = updateChain.exec(context)
    suspend fun delete(context: UserContext) = deleteChain.exec(context)
}
