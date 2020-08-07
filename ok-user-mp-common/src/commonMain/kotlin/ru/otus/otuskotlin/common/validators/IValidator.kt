package ru.otus.otuskotlin.common.validators

interface IValidator<T> {
    fun validate(arg: T): ValidationResult
}
