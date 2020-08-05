package ru.otus.otuskotlin.user.common

import ru.otus.otuskotlin.user.common.models.UserModel

data class UserContext(
        var requestUserId: String = "",
        var requestUser: UserModel = UserModel.NONE,
        var responseUser: UserModel = UserModel.NONE,
        var status: UserContextStatus = UserContextStatus.NONE
)
