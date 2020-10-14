package ru.otus.otuskotlin.user.backend.logics.handlers

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserIndexStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import java.time.LocalDate

val stubIndex = cor<UserContext> {
    isApplicable { stubIndexCase != UserIndexStubCases.NONE }
    handler {
        isApplicable { stubIndexCase == UserIndexStubCases.SUCCESS }
        exec {
            responseUsers = mutableListOf(
                    UserModel(
                            id = "ivanov-id",
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
                    ),
                    UserModel(
                            id = "petrov-id",
                            fname = "Petr",
                            mname = "Petrovich",
                            lname = "Petrov",
                            dob = LocalDate.parse("2000-01-02"),
                            email = "petr@Petrov.example",
                            phone = "+7 999 999 9998",
                            permissions = mutableSetOf(
                                    UserPermissionsModel.SEND_MESSAGE,
                                    UserPermissionsModel.UPDATE,
                                    UserPermissionsModel.GET_NEWS,
                                    UserPermissionsModel.VIEW
                            )
                    )
            )
            status = UserContextStatus.FINISHING
        }
    }

}
