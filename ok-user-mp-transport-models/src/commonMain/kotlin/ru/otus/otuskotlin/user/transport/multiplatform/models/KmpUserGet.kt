package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class KmpUserGet(
        val userId: String? = null,
        val debug: Debug? = null
) {
    @Serializable
    data class Debug(
            val stub: StubCases? = null,
            val db: KmpUserDbModes? = null,
    ) {

    }

    @Serializable
    enum class StubCases {
        NONE,
        SUCCESS
    }
}
