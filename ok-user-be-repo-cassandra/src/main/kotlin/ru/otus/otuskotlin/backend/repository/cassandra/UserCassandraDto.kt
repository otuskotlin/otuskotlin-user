package ru.otus.otuskotlin.backend.repository.cassandra

import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec
import com.datastax.driver.mapping.annotations.*
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.TABLE_NAME
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import java.time.Instant
import java.time.LocalDate

@Table(name = TABLE_NAME)
data class UserCassandraDto(
        @PartitionKey(0)
        @Column(name = COLUMN_ID)
        val id: String? = null,

        @Column(name = COLUMN_NAME_FULL)
        val fullName: String? = null,

        @Column(name = COLUMN_NAME_FIRST)
        val fname: String? = null,

        @Column(name = COLUMN_NAME_MIDDLE)
        val mname: String? = null,

        @Column(name = COLUMN_NAME_LAST)
        val lname: String? = null,

        @Column(name = COLUMN_CONTACTS_PHONE)
        val phone: String? = null,

        @Column(name = COLUMN_CONTACTS_EMAIL)
        val email: String? = null,

        @Column(name = COLUMN_BIRTH_DATE, codec = LocalDateCodec::class)
        val dob: LocalDate? = null
) {
    fun toModel(): UserModel = UserModel(
            id = id ?: "",
            fname = fname ?: "",
            mname = mname ?: "",
            lname = lname ?: "",
            dob = dob ?: LocalDate.MIN,
            email = email ?: "",
            phone = phone ?: ""
    )

    companion object {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME_FULL = "full_name"
        const val COLUMN_NAME_FIRST = "fname"
        const val COLUMN_NAME_MIDDLE = "mname"
        const val COLUMN_NAME_LAST = "lname"
        const val COLUMN_CONTACTS_PHONE = "phone"
        const val COLUMN_CONTACTS_EMAIL = "email"
        const val COLUMN_BIRTH_DATE = "dob"

        fun of(user: UserModel) = of(user, user.id)

        fun of(user: UserModel, id: String) = UserCassandraDto(
                id = id.takeIf { it.isNotBlank() },
                fullName = "${user.lname} ${user.fname} ${user.mname}",
                fname = user.fname.takeIf { it.isNotBlank() },
                mname = user.mname.takeIf { it.isNotBlank() },
                lname = user.lname.takeIf { it.isNotBlank() },
                phone = user.phone.takeIf { it.isNotBlank() },
                email = user.email.takeIf { it.isNotBlank() },
                dob = user.dob.takeIf { it != LocalDate.MIN }
        )
    }

}
