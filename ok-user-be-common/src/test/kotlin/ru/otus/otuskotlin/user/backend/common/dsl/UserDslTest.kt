package ru.otus.otuskotlin.user.backend.common.dsl

import ru.otus.otuskotlin.user.backend.common.models.UserModel
import java.time.LocalDate
import kotlin.test.Test

class UserDslTest {
    @Test
    fun userDslTest() {
        val user: UserModel = user {
            name {
                first "Иван"
                second "Иванович"
                last "Ивавнов"
            }
            birth {
                date LocalDate.parse("2000-01-01")
            }
            contracts {
                email "ivan@ivanov.example"
                phone "+7 999 999 9999"
            }

            permission {
                permission "VIEW"
                permission UserPermissionsModel.SEND_MESSAGE
            }
        }

    }
}
