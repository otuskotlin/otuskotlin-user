package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlinx.serialization.Serializable

@Serializable
class KmpUserCreate(
        override var fname: String? = null,
        override var mname: String? = null,
        override var lname: String? = null,
        override var dob: String? = null,
        override var email: String? = null,
        override var phone: String? = null,
        var debug: Debug? = null
) : KmpUserSave(
        id = null,
        fname = fname,
        mname = mname,
        lname = lname,
        dob = dob,
        email = email,
        phone = phone
) {
    @Serializable
    class Debug
}
