package ru.otus.otuskotlin.user.backend.common.dsl

import ru.otus.otuskotlin.user.backend.common.models.UserModel

class UserDsl {

    private var name = UserNameDsl.EMPTY
    private var birth = UserBirthDsl.EMPTY
    private var contacts = UserContactsDsl.EMPTY
    private var permissions = UserPermissionsDsl.EMPTY

    fun build(): UserModel = UserModel(

    )

    fun name(conf: UserNameDsl.() -> Unit) {
        name = UserNameDsl().apply(conf)
    }

    fun birth(conf: UserBirthDsl.() -> Unit) {
        birth = UserBirthDsl().apply(conf)
    }

    fun contracts(conf: UserContactsDsl.() -> Unit) {
        contacts = UserContactsDsl().apply(conf)
    }

    fun permission(conf: UserPermissionsDsl.() -> Unit) {
        permissions = UserPermissionsDsl().apply(conf)
    }
}

fun user(conf: UserDsl.() -> Unit) = UserDsl().apply(conf).build()
