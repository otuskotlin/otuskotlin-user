package ru.otus.otuskotlin.user.backend.common

enum class UserContextStatus {
    NONE,
    RUNNING,
    FINISHING,
    FAILING,
    SUCCESS,
    ERROR
}
