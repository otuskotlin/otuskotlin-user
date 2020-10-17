package ru.otus.otuskotlin.backend.repository.dynamodb

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import io.kotless.PermissionLevel
import io.kotless.dsl.lang.DynamoDBTable as KotlessTable
import ru.otus.otuskotlin.backend.repository.dynamodb.UserDynamoDto.Companion.TABLE_NAME
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import java.time.LocalDate


@DynamoDBTable(tableName = TABLE_NAME)
@KotlessTable(table = TABLE_NAME, level = PermissionLevel.ReadWrite)
data class UserDynamoDto(
        @DynamoDBHashKey(attributeName = COLUMN_ID)
        var id: String? = null,

        @DynamoDBAttribute(attributeName = COLUMN_NAME_FULL)
        var fullName: String? = null,

        @DynamoDBAttribute(attributeName = COLUMN_NAME_FIRST)
        var fname: String? = null,

        @DynamoDBAttribute(attributeName = COLUMN_NAME_MIDDLE)
        var mname: String? = null,

        @DynamoDBAttribute(attributeName = COLUMN_NAME_LAST)
        var lname: String? = null,

        @DynamoDBAttribute(attributeName = COLUMN_BIRTH_DATE)
        var dob: String? = null,

        @DynamoDBAttribute(attributeName = COLUMN_CONTACTS_EMAIL)
        var email: String? = null,

        @DynamoDBAttribute(attributeName = COLUMN_CONTACTS_PHONE)
        var phone: String? = null
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

                fun of(user: UserModel, id: String) = UserDynamoDto(
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
