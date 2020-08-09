package ru.otus.otuskotlin.user.transport.multiplatform.backend

import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.transport.multiplatform.models.KmpUserUpdate
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class MappersKtTest {

    @Test
    fun userToModelTest() {
        val kmpUser = KmpUserUpdate(
                id = "123",
                fname = "Ivan",
                mname = null,
                lname = "Ivanov",
                dob = null,
                email = "  ",
                phone = "+7904"
        )

        val modelUser = kmpUser.model()
        assertEquals("123", modelUser.id)
        assertEquals("Ivan", modelUser.fname)
        assertEquals("", modelUser.mname)
        assertEquals("Ivanov", modelUser.lname)
        assertEquals(LocalDate.MIN, modelUser.dob)
        assertEquals("", modelUser.email)
        assertEquals("+7904", modelUser.phone)
        assertTrue { modelUser.permissions.isEmpty() }
    }

    @Test
    fun userToKmpTest() {
        val modelUser = UserModel(
                id = "123",
                fname = "Ivan",
                mname = "",
                lname = "Ivanov",
                dob = LocalDate.MIN,
                email = "  ",
                phone = "+7904",
                permissions = mutableSetOf(
                        UserPermissionsModel.VIEW,
                        UserPermissionsModel.SEND_MESSAGE
                )
        )

        val kmpUser = modelUser.kmp()
        assertEquals("123", kmpUser.id)
        assertEquals("Ivan", kmpUser.fname)
        assertEquals(null, kmpUser.mname)
        assertEquals("Ivanov", kmpUser.lname)
        assertEquals(null, kmpUser.dob)
        assertEquals(null, kmpUser.email)
        assertEquals("+7904", kmpUser.phone)
        assertTrue {
            kmpUser.permissions
                    ?.containsAll(listOf(
                            "VIEW",
                            "SEND_MESSAGE"
                    ))
                    ?: false
        }
    }
}
