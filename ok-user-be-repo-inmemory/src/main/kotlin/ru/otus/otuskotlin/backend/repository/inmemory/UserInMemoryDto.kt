package ru.otus.otuskotlin.backend.repository.inmemory

import ru.otus.otuskotlin.user.backend.common.models.UserModel
import java.time.LocalDate

data class UserInMemoryDto(
        val id: String? = null,
        val fullName: String? = null,
        val fname: String? = null,
        val mname: String? = null,
        val lname: String? = null,
        val phone: String? = null,
        val email: String? = null,
        val dob: String? = null,
        val state: UserInMemoryStates? = null
) {
    fun toModel(): UserModel = UserModel(
            id = id ?: "",
            fname = fname ?: "",
            mname = mname ?: "",
            lname = lname ?: "",
            dob = dob?.let { LocalDate.parse(it) } ?: LocalDate.MIN,
            email = email ?: "",
            phone = phone ?: ""
    )

    companion object {
        fun of(user: UserModel) = of(user, user.id)

        fun of(user: UserModel, id: String) = UserInMemoryDto(
                id = id.takeIf { it.isNotBlank() },
                fullName = "${user.lname} ${user.fname} ${user.mname}",
                fname = user.fname.takeIf { it.isNotBlank() },
                mname = user.mname.takeIf { it.isNotBlank() },
                lname = user.lname.takeIf { it.isNotBlank() },
                phone = user.phone.takeIf { it.isNotBlank() },
                email = user.email.takeIf { it.isNotBlank() },
                dob = user.dob.takeIf { it != LocalDate.MIN }?.toString()
        )
    }

}
