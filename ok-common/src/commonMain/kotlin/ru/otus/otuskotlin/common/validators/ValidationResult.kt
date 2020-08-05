package ru.otus.otuskotlin.common.validators

data class ValidationResult(
        val errors: List<HandleError> = emptyList()
) {

    constructor(vararg argErrors: HandleError?): this(errors = argErrors.filterNotNull())

    val level: HandleError.Level
        get() = errors.maxBy { it.level.lvl }?.level ?: HandleError.Level.NONE

    val isOk
        get() = level.lvl < HandleError.Level.ERROR.lvl
}
