package ru.otus.otuskotlin.user.backend.common.dsl

import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel

class UserPermissionsDsl {

    private val permissions: MutableSet<UserPermissionsModel> = mutableSetOf()

    fun add(value: UserPermissionsModel) {
        permissions += value
    }

    fun add(strValue: String) {
        add(UserPermissionsModel.valueOf(strValue))
    }

    operator fun String.unaryPlus() = add(this)
    operator fun UserPermissionsModel.unaryPlus() = add(this)

    fun get() = permissions.toSet()

    companion object {
        val EMPTY = UserPermissionsDsl()
    }
}
