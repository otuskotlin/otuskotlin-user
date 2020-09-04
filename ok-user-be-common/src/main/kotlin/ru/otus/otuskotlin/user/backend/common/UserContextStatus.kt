package ru.otus.otuskotlin.user.backend.common

enum class UserContextStatus {
    NONE,
    RUNNING,
    FINISHING,
    FAILING,
    SUCCESS,
    ERROR;

    val isError
        get() = this in arrayOf(FAILING, ERROR)
}
