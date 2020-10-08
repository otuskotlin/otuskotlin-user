package ru.otus.otuskotlin.user.backend.common.repositories

import ru.otus.otuskotlin.user.backend.common.models.UserIndexFilter
import ru.otus.otuskotlin.user.backend.common.models.UserModel

interface IUserRepository {

    fun get(id: String): UserModel
    fun index(filter: UserIndexFilter): Collection<UserModel>
    fun create(user: UserModel): UserModel
    fun update(user: UserModel): UserModel
    fun delete(id: String): UserModel

    companion object {
        val NONE = object: IUserRepository {
            override fun get(id: String): UserModel {
                TODO("Not yet implemented")
            }

            override fun index(filter: UserIndexFilter): Collection<UserModel> {
                TODO("Not yet implemented")
            }

            override fun create(user: UserModel): UserModel {
                TODO("Not yet implemented")
            }

            override fun update(user: UserModel): UserModel {
                TODO("Not yet implemented")
            }

            override fun delete(id: String): UserModel {
                TODO("Not yet implemented")
            }

        }

    }
}
