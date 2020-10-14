package ru.otus.otuskotlin.user.backend.logics.handlers

import ru.otus.otuskotlin.common.cor.corHandler
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.models.WorkModes

val querySetWorkMode = corHandler<UserContext> {
    exec {
        userRepo = when(workMode) {
            WorkModes.TEST -> userRepoTest
            WorkModes.PROD -> userRepoProd
        }
    }
}
