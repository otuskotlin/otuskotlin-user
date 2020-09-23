package ru.otus.otuskotlin.user.backend.common.dsl

import ru.otus.otuskotlin.user.backend.common.UserContext
import kotlin.test.Test
import kotlin.test.assertEquals

internal class InfixKtTest {
    @Test
    fun powerOfTest() {
        val res = 2.00 powerOf 2.00

        assertEquals(4.0, res)
    }

    @Test
    fun contextTest() {
        val userContext = UserContext()

        userContext applyRequest user {
            name {
                first = "Иван"
            }
        }

        assertEquals("Иван", userContext.requestUser.fname)
    }
}
