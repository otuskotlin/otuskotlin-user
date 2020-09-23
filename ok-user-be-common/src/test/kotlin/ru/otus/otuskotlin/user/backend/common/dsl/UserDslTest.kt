package ru.otus.otuskotlin.user.backend.common.dsl

import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserDslTest {
    @Test
    fun userDslTest() {
        val user: UserModel = user {
            name {
                first = "Иван"
                second = "Иванович"
                last = "Ивавнов"
            }
            birth {
                date = LocalDate.parse("2000-01-01")
            }
            contracts {
                email = "ivan@ivanov.example"
                phone = "+7 999 999 9999"
            }

            permission {
                add("VIEW")
                add(UserPermissionsModel.SEND_MESSAGE)
                + "UPDATE"
                + UserPermissionsModel.GET_NEWS
            }
        }

        assertEquals("Иванович", user.mname)
        assertEquals("2000-01-01", user.dob.toString())
        assertEquals("ivan@ivanov.example", user.email)
        assertTrue("permission must contain VIEW") {
            user.permissions.containsAll(listOf(
                    UserPermissionsModel.VIEW,
                    UserPermissionsModel.SEND_MESSAGE,
                    UserPermissionsModel.UPDATE,
                    UserPermissionsModel.GET_NEWS
            ))
        }
    }
}
