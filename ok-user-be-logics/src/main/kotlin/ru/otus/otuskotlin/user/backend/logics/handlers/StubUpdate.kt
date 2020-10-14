package ru.otus.otuskotlin.user.backend.logics.handlers

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.backend.common.models.UserUpdateStubCases


val stubUpdate = cor<UserContext> {
    isApplicable { stubUpdateCase != UserUpdateStubCases.NONE }
    handler {
        isApplicable { stubUpdateCase == UserUpdateStubCases.SUCCESS }
        exec {
            responseUser = UserModel(
                    id = requestUser.id,
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
