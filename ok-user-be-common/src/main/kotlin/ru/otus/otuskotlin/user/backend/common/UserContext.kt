package ru.otus.otuskotlin.user.backend.common

import ru.otus.otuskotlin.user.backend.common.models.*

data class UserContext(
        var requestUserId: String = "",
        var requestUser: UserModel = UserModel.NONE,
        var stubGetCase: UserGetStubCases = UserGetStubCases.NONE,
        var stubIndexCase: UserIndexStubCases = UserIndexStubCases.NONE,
        var stubCreateCase: UserCreateStubCases = UserCreateStubCases.NONE,
        var stubUpdateCase: UserUpdateStubCases = UserUpdateStubCases.NONE,
        var stubDeleteCase: UserDeleteStubCases = UserDeleteStubCases.NONE,
        var responseUser: UserModel = UserModel.NONE,
        var responseUsers: MutableList<UserModel> = mutableListOf(),
        val errors: MutableList<IUserError> = mutableListOf(),
        var status: UserContextStatus = UserContextStatus.NONE
)
