package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class KmpUserError(
        val code: String? = null,
        val group: String? = null,
        val field: String? = null,
        val level: Level? = null,
        val message: String? = null
) {
    @Serializable
    enum class Level {
        SUCCESS,
        ERROR
    }
}
