package ru.otus.otuskotlin.user.backend.common.dsl

import ru.otus.otuskotlin.user.backend.common.models.UserModel

@UserDslMarker
class UserDsl {

    private var id: String = ""
    private var name = UserNameDsl.EMPTY
    private var birth = UserBirthDsl.EMPTY
    private var contacts = UserContactsDsl.EMPTY
    private var permissions = UserPermissionsDsl.EMPTY

    fun build(): UserModel = UserModel(
            id = id,
            fname = name.first,
            mname = name.second,
            lname = name.last,
            dob = birth.date,
            email = contacts.email,
            phone = contacts.phone,
            permissions = permissions.get().toMutableSet()
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
