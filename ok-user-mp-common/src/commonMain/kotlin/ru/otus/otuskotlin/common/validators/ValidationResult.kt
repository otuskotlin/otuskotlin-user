package ru.otus.otuskotlin.common.validators

import kotlin.jvm.*


data class ValidationResult
@JvmOverloads
constructor(
        val errors: List<HandleError> = emptyList()
) {

    constructor(vararg argErrors: HandleError?): this(errors = argErrors.filterNotNull())

    val level: HandleError.Level
        get() = errors.maxBy { it.level.lvl }?.level ?: HandleError.Level.NONE

    val isOk
        get() = level.lvl < HandleError.Level.ERROR.lvl

    companion object {
        @JvmStatic
        @get:JvmName("getSUCCESS")
        val SUCCESS = ValidationResult()

        @JvmField
        val OK = SUCCESS
    }
}
