package ru.otus.otuskotlin.common.cor

interface IExec<T> {
    suspend fun exec(ctx: T)
}
