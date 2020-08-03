package ru.otus.otuskotlin.user.common

import ru.otus.otuskotlin.user.common.models.UserModel

data class UserContext(
        var queryUserId: String = "",
        var responseUser: UserModel = UserModel.NONE
)
