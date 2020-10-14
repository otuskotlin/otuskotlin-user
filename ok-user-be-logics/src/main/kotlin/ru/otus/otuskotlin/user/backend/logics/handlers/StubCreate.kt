package ru.otus.otuskotlin.user.backend.logics.handlers

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserCreateStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel

val stubCreate = cor<UserContext> {
    isApplicable { stubCreateCase != UserCreateStubCases.NONE }
    handler {
        isApplicable { stubCreateCase == UserCreateStubCases.SUCCESS }
        exec {
            responseUser = UserModel(
                    id = "test-create-id",
                    fname = requestUser.fname,
                    mname = requestUser.mname,
                    lname = requestUser.lname,
                    dob = requestUser.dob,
                    email = requestUser.email,
                    phone = requestUser.phone,
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
