package ru.otus.otuskotlin.user.backend.common.models

enum class WorkModes {
    PROD,
    TEST;

    companion object {
        val DEFAULT=PROD
    }
}
