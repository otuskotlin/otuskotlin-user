package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class KmpUserIndex(
        var limit: Long? = null,
        var offset: Long? = null,
        var filter: Filter? = null,
        var debug: Debug? = null
) {
    @Serializable
    data class Filter(
            var searchString: String? = null,
            var dob: String? = null
    )

    @Serializable
    data class Debug(
            val stub: StubCases? = null,
            val db: KmpUserDbModes? = null
    )

    @Serializable
    enum class StubCases {
        NONE,
        SUCCESS
    }
}
