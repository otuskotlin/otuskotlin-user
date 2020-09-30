package ru.otus.otuskotlin.user.backend.common.dsl

@UserDslMarker
class UserContactsDsl(
        var email: String = "",
        var phone: String = ""
) {
    companion object {
        val EMPTY = UserContactsDsl()
    }
}
