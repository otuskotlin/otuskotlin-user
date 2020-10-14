package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class KmpUserCreate(
        override var fname: String? = null,
        override var mname: String? = null,
        override var lname: String? = null,
        override var dob: String? = null,
        override var email: String? = null,
        override var phone: String? = null,
        var debug: Debug? = null
) : KmpUserSave(
        fname = fname,
        mname = mname,
        lname = lname,
        dob = dob,
        email = email,
        phone = phone
) {
    @Serializable
    data class Debug(
            val stub: StubCases? = null,
            val db: KmpUserDbModes? = null,
    )

    @Serializable
    enum class StubCases {
        NONE,
        SUCCESS
    }
}
