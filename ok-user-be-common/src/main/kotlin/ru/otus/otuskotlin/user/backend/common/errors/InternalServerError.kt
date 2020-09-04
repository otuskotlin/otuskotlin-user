package ru.otus.otuskotlin.user.backend.common.errors

import ru.otus.otuskotlin.user.backend.common.models.IUserError

data class InternalServerError(
        override val code: String = "internal-error",
        override val group: IUserError.Groups = IUserError.Groups.SERVER,
        override val field: String = "",
        override val level: IUserError.Levels = IUserError.Levels.ERROR,
        override val message: String = "Internal server error. If it continues to rise, please, apply to the Administrator"
) : IUserError {
    companion object {
        val instance = InternalServerError()
    }
}
