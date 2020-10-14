package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository

class UserCrud(
        userRepoProd: IUserRepository = IUserRepository.NONE,
        userRepoTest: IUserRepository = IUserRepository.NONE
) {
    private val getChain = UserGetChain(userRepoProd = userRepoProd, userRepoTest = userRepoTest)
    private val indexChain = UserIndexChain(userRepoProd = userRepoProd, userRepoTest = userRepoTest)
    private val createChain = UserCreateChain(userRepoProd = userRepoProd, userRepoTest = userRepoTest)
    private val updateChain = UserUpdateChain(userRepoProd = userRepoProd, userRepoTest = userRepoTest)
    private val deleteChain = UserDeleteChain(userRepoProd = userRepoProd, userRepoTest = userRepoTest)

    suspend fun get(context: UserContext) = getChain.exec(context)
    suspend fun index(context: UserContext) = indexChain.exec(context)
    suspend fun create(context: UserContext) = createChain.exec(context)
    suspend fun update(context: UserContext) = updateChain.exec(context)
    suspend fun delete(context: UserContext) = deleteChain.exec(context)
}
