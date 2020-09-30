package ru.otus.otuskotlin.user.backend.common.dsl

import java.time.LocalDate

@UserDslMarker
class UserBirthDsl(
        var date: LocalDate = LocalDate.MIN
) {
    companion object {
        val EMPTY = UserBirthDsl()
    }
}
