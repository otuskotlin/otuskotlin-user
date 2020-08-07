package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
enum class KmpUserResultStatuses {
    SUCCESS,
    ERROR,
    WARNING
}
