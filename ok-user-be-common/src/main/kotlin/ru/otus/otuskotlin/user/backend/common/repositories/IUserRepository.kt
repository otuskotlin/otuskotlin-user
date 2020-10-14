package ru.otus.otuskotlin.user.backend.common.repositories

import ru.otus.otuskotlin.user.backend.common.models.UserIndexFilter
import ru.otus.otuskotlin.user.backend.common.models.UserModel

interface IUserRepository {

    suspend fun get(id: String): UserModel
    suspend fun index(filter: UserIndexFilter): Collection<UserModel>
    suspend fun create(user: UserModel): UserModel
    suspend fun update(user: UserModel): UserModel
    suspend fun delete(id: String): UserModel

    companion object {
        val NONE = object: IUserRepository {
            override suspend fun get(id: String): UserModel {
                TODO("Not yet implemented")
            }

            override suspend fun index(filter: UserIndexFilter): Collection<UserModel> {
                TODO("Not yet implemented")
            }

            override suspend fun create(user: UserModel): UserModel {
                TODO("Not yet implemented")
            }

            override suspend fun update(user: UserModel): UserModel {
                TODO("Not yet implemented")
            }

            override suspend fun delete(id: String): UserModel {
                TODO("Not yet implemented")
            }

        }

    }
}
