package ru.otus.otuskotlin.user.backend.common.errors

import ru.otus.otuskotlin.user.backend.common.models.IUserError

data class QueryParseError(
        override val code: String = "",
        override val group: IUserError.Groups,
        override val field: String = "",
        override val level: IUserError.Levels = IUserError.Levels.ERROR,
        override val message: String = ""
) : IUserError {
    constructor(
            code: String,
            group: IUserError.Groups = IUserError.Groups.VALIDATION,
            field: String = "",
            e: Throwable
    ): this(
            code = code,
            group = group ,
            field = field,
            level = IUserError.Levels.ERROR,
            message = e.message ?: "Query parse Error"
    )
}
