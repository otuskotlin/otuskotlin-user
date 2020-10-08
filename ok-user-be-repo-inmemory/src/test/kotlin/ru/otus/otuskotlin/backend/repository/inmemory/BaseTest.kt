package ru.otus.otuskotlin.backend.repository.inmemory

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.user.backend.common.dsl.user
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

class BaseTest {
    @OptIn(ExperimentalTime::class)
    @Test
    fun baseTest() {
        val repo = UserRepositoryInMemoty(
                ttl = 2.toDuration(DurationUnit.HOURS)
        )

        val user = user {
            name {
                first = "Ivan"
                second = "Ivanovich"
                last = "Ivanov"
            }
            birth {
                date = LocalDate.parse("2000-01-01")
            }
            contracts {
                phone = "+7 999 999 9999"
                email = "ivan@ivanov.example"
            }
        }

        runBlocking {
            val userCreate = repo.create(user)
            assertEquals(user.fname, userCreate.fname)
            assertEquals(user.mname, userCreate.mname)
            assertEquals(user.lname, userCreate.lname)
            assertEquals(user.phone, userCreate.phone)
            assertEquals(user.email, userCreate.email)
            assertEquals(user.dob, userCreate.dob)

            val userGet = repo.get(userCreate.id)
            assertEquals(userCreate.id, userGet.id)
            assertEquals(user.fname, userGet.fname)
            assertEquals(user.mname, userGet.mname)
            assertEquals(user.lname, userGet.lname)
            assertEquals(user.phone, userGet.phone)
            assertEquals(user.email, userGet.email)
            assertEquals(user.dob, userGet.dob)
        }
    }
}
