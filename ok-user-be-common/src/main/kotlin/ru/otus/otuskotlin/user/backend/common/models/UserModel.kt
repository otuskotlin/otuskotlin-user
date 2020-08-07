package ru.otus.otuskotlin.user.backend.common.models

import java.time.LocalDate

data class UserModel(
        var id: String = "",
        var fname: String = "",
        var mname: String = "",
        var lname: String = "",
        var dob: LocalDate = LocalDate.MIN,
        var email: String = "",
        var phone: String = ""
) {
    companion object {
        val NONE = UserModel()
    }
}
