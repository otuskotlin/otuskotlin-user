package ru.otus.otuskotlin.user.backend.common

import ru.otus.otuskotlin.user.backend.common.models.*
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository

data class UserContext(
        var userRepo: IUserRepository = IUserRepository.NONE,
        var userRepoProd: IUserRepository = IUserRepository.NONE,
        var userRepoTest: IUserRepository = IUserRepository.NONE,
        var workMode: WorkModes = WorkModes.DEFAULT,

        var requestUserId: String = "",
        var requestUser: UserModel = UserModel.NONE,
        var requestUserFilter: UserIndexFilter = UserIndexFilter.NONE,
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
