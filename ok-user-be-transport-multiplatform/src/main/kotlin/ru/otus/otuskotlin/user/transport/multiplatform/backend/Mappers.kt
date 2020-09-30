package ru.otus.otuskotlin.user.transport.multiplatform.backend

import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.models.*
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import java.time.LocalDate

fun UserContext.setQuery(save: KmpUserSave) = this.apply {
    requestUser = save.model()
    when (save) {
        is KmpUserUpdate -> stubUpdateCase = when(save.debug?.stub) {
            KmpUserUpdate.StubCases.SUCCESS -> UserUpdateStubCases.SUCCESS
            else -> UserUpdateStubCases.NONE
        }
        is KmpUserCreate -> stubCreateCase = when(save.debug?.stub) {
            KmpUserCreate.StubCases.SUCCESS -> UserCreateStubCases.SUCCESS
            else -> UserCreateStubCases.NONE
        }
    }
}

fun UserContext.setQuery(get: KmpUserGet) = this.apply {
    requestUserId = get.userId ?: ""
    stubGetCase = when(get.debug?.stub) {
        KmpUserGet.StubCases.SUCCESS -> UserGetStubCases.SUCCESS
        else -> UserGetStubCases.NONE
    }
}

fun UserContext.setQuery(del: KmpUserDelete) = this.apply {
    requestUserId = del.userId ?: ""
    stubDeleteCase = when(del.debug?.stub) {
        KmpUserDelete.StubCases.SUCCESS -> UserDeleteStubCases.SUCCESS
        else -> UserDeleteStubCases.NONE
    }
}

fun UserContext.setQuery(index: KmpUserIndex) = this.apply {
//    filter = index.filter ?: UserModel.Filter
    stubIndexCase = when(index.debug?.stub) {
        KmpUserIndex.StubCases.SUCCESS -> UserIndexStubCases.SUCCESS
        else -> UserIndexStubCases.NONE
    }
}

fun UserContext.resultItem(): KmpUserResponseItem = KmpUserResponseItem(
        data = responseUser.kmp(),
        errors = errors.map { it.kmp() },
        status = kmpStatus()
)

fun UserContext.resultIndex(): KmpUserResponseIndex = KmpUserResponseIndex(
        data = responseUsers.map { it.kmp() },
        errors = errors.map { it.kmp() },
        status = KmpUserResultStatuses.SUCCESS
)

fun KmpUserSave.model() = UserModel(
        id = if (this is KmpUserUpdate) (id ?: "") else "",
        fname = fname.modelToString(),
        mname = mname.modelToString(),
        lname = lname.modelToString(),
        dob = dob.modelToLocalDate(),
        email = email.modelToString(),
        phone = phone.modelToString()
)

fun UserModel.kmp() = KmpUser(
        id = id.kmpToString(),
        fname = fname.kmpToString(),
        mname = mname.kmpToString(),
        lname = lname.kmpToString(),
        dob = dob.kmpToString(),
        email = email.kmpToString(),
        phone = phone.kmpToString(),
        permissions = permissions.map { it.toString() }.toMutableSet()
)

fun IUserError.kmp() = KmpUserError(
        code = code.takeIf { it.isNotBlank() },
        group = group.takeIf { it != IUserError.Groups.NONE }?.toString(),
        field = field.takeIf { it.isNotBlank() },
        level = level.kmp(),
        message = message.takeIf { it.isNotBlank() }
)

fun IUserError.Levels.kmp() = when (this) {
    IUserError.Levels.ERROR, IUserError.Levels.FATAL -> KmpUserError.Level.ERROR
    else -> KmpUserError.Level.SUCCESS
}

fun UserContext.kmpStatus() = when {
    status.isError || errors.any { it.level.isError } -> KmpUserResultStatuses.ERROR
    errors.any { it.level.isWarning } -> KmpUserResultStatuses.WARNING
    else -> KmpUserResultStatuses.SUCCESS
}

            private fun String?.modelToString() = this?.takeIf { it.isNotBlank() } ?: ""
    private fun String?.modelToLocalDate() = this
            ?.takeIf { it.isNotBlank() }
            ?.let { LocalDate.parse(it) }
            ?: LocalDate.MIN

            private fun String.kmpToString() = this.takeIf { it.isNotBlank() }
                    private fun LocalDate.kmpToString(): String? = this.takeIf { it != LocalDate.MIN }?.toString()
