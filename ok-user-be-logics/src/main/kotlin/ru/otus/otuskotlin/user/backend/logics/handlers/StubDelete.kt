package ru.otus.otuskotlin.user.backend.logics.handlers

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserDeleteStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import java.time.LocalDate

val stubDelete = cor<UserContext> {
    isApplicable { stubDeleteCase != UserDeleteStubCases.NONE }
    handler {
        isApplicable { stubDeleteCase == UserDeleteStubCases.SUCCESS }
        exec {
            responseUser = UserModel(
                    id = requestUserId,
                    fname = "Ivan",
                    mname = "Ivanovich",
                    lname = "Ivanov",
                    dob = LocalDate.parse("2000-01-01"),
                    email = "ivan@ivanov.example",
                    phone = "+7 999 999 9999",
                    permissions = mutableSetOf(
                            UserPermissionsModel.SEND_MESSAGE,
                            UserPermissionsModel.UPDATE,
                            UserPermissionsModel.GET_NEWS,
                            UserPermissionsModel.VIEW
                    )
            )
            status = UserContextStatus.FINISHING
        }
    }
}
