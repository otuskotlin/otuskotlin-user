package ru.otus.otuskotlin.user.backend.common

import ru.otus.otuskotlin.user.backend.common.models.UserModel

data class UserContext(
        var queryUserId: String = "",
        var responseUser: UserModel = UserModel.NONE
)
