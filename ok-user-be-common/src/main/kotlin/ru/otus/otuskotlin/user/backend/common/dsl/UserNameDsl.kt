package ru.otus.otuskotlin.user.backend.common.dsl

@UserDslMarker
class UserNameDsl(
        var first: String = "",
        var second: String = "",
        var last: String = ""
) {
    companion object {
        val EMPTY = UserNameDsl()
    }
}
