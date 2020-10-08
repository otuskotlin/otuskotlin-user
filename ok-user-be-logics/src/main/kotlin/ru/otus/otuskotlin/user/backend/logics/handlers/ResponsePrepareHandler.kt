package ru.otus.otuskotlin.user.backend.logics.handlers

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus

val responsePrepareHandler = cor<UserContext> {
    handler {
        isApplicable { status in arrayOf(UserContextStatus.RUNNING, UserContextStatus.FINISHING) }
        exec {
            status = UserContextStatus.SUCCESS
        }
    }
    handler {
        isApplicable { status != UserContextStatus.SUCCESS }
        exec { status = UserContextStatus.ERROR }
    }
}
