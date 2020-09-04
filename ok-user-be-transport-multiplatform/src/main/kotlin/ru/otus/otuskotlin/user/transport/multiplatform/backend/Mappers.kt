package ru.otus.otuskotlin.user.transport.multiplatform.backend

import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.models.IUserError
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.transport.multiplatform.models.*
import java.time.LocalDate

fun UserContext.setQuery(save: KmpUserSave) = this.apply {
    requestUser = save.model()
}

fun UserContext.setQuery(get: KmpUserGet) = this.apply {
    requestUserId = get.userId ?: ""
}

fun UserContext.setQuery(del: KmpUserDelete) = this.apply {
    requestUserId = del.userId ?: ""
}

fun UserContext.setQuery(index: KmpUserIndex) = this.apply {
//    filter = index.filter ?: UserModel.Filter
}

fun UserContext.resultItem(): KmpUserResponseItem = KmpUserResponseItem(
        data = responseUser.kmp(),
        errors = errors.map { it.kmp() },
        status = kmpStatus()
)

fun UserContext.resultIndex(): KmpUserResponseIndex = KmpUserResponseIndex(
        data = listOf(responseUser.kmp()),
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
