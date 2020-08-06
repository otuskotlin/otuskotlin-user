package ru.otus.otuskotlin.user.transport.multiplatform.models

import kotlin.test.Test
import kotlin.test.assertEquals

internal class KmpUserTest {
    @Test
    fun createKmpUser() {
        val user = KmpUser(
                id = "x1",
                fname = "Ivan",
                lname = "Ivanov",
                mname = "P",
                dob = "2000-01-01",
                email = "ivanov@ivanov.dom",
                phone = "+7 999",
                permissions = mutableSetOf(
                        "perm1",
                        "perm2"
                )
        )

        assertEquals("x1", user.id)
    }
}
