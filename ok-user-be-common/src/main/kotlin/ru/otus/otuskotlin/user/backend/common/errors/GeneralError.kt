package ru.otus.otuskotlin.user.backend.common.errors

import ru.otus.otuskotlin.user.backend.common.models.IUserError

data class GeneralError(
        override val code: String = "",
        override val group: IUserError.Groups,
        override val field: String = "",
        override val level: IUserError.Levels = IUserError.Levels.ERROR,
        override val message: String = ""
) : IUserError {
    constructor(
            code: String,
            group: IUserError.Groups = IUserError.Groups.SERVER,
            e: Throwable
    ): this(
            code = "",
            group = IUserError.Groups.SERVER ,
            field = "",
            level = IUserError.Levels.ERROR,
            message = e.message ?: "Unknown exception"
    )
}
