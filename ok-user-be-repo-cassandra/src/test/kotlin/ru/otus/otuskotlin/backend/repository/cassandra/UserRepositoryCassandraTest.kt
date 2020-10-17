package ru.otus.otuskotlin.backend.repository.cassandra

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.testcontainers.containers.GenericContainer
import ru.otus.otuskotlin.user.backend.common.models.UserIndexFilter
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import java.time.Duration
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CassandraContainer : GenericContainer<CassandraContainer>("cassandra")

internal class UserRepositoryCassandraTest {

    companion object {
        private val PORT = 9042
        private val keyspace = "test_keyspace"
        private lateinit var container: CassandraContainer
        private lateinit var repo: UserRepositoryCassandra

        @BeforeClass
        @JvmStatic
        fun tearUp() {
            container = CassandraContainer()
                    .withExposedPorts(PORT)
                    .withStartupTimeout(Duration.ofSeconds(40L))
                    .apply {
                        start()
                    }

            repo = UserRepositoryCassandra(
                    keyspace = keyspace,
                    hosts = container.host,
                    port = container.getMappedPort(PORT),
                    initObjects = listOf(
                            UserModel(id = "get-id", fname = "Ivan"),
                            UserModel(id = "update-id"),
                            UserModel(id = "delete-id", fname = "Ivan"),
                            UserModel(id = "index-id-1", dob = LocalDate.parse("2020-01-01"), fname = "Ivan"),
                            UserModel(id = "index-id-2", dob = LocalDate.parse("2020-01-01"), fname = "Petr"),
                            UserModel(id = "index-id-3", dob = LocalDate.parse("2020-01-01"), fname = "John")
                    )
            )

            repo.init()
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            container.close()
        }
    }

    @Test
    fun indexTest() {
        runBlocking {
            val obj = repo.index(UserIndexFilter(
                    dob = "2020-01-01"
            )).sortedBy { it.id }
            assertEquals(listOf("index-id-1", "index-id-2", "index-id-3"), obj.map { it.id })
            assertEquals("Ivan", obj.first().fname)
        }
    }

    @Test
    fun createTest() {
        runBlocking {
            val obj = repo.create(UserModel(
                    fname = "Ivan",
                    mname = "Ivanovich",
                    lname = "Ivanov",
                    dob = LocalDate.parse("2020-01-02"),
                    phone = "+7 999 999 9999",
                    email = "ivan@ivanov.example"
            ))
            assertNotNull(obj.id)
            assertEquals("Ivan", obj.fname)
            assertEquals("Ivanovich", obj.mname)
            assertEquals("Ivanov", obj.lname)
            assertEquals(LocalDate.parse("2020-01-02"), obj.dob)
            assertEquals("+7 999 999 9999", obj.phone)
            assertEquals("ivan@ivanov.example", obj.email)
        }
    }

    @Test
    fun updateTest() {
        runBlocking {
            val obj = repo.update(UserModel(
                    id = "update-id",
                    fname = "Ivan",
                    mname = "Ivanovich",
                    lname = "Ivanov",
                    dob = LocalDate.parse("2020-01-02"),
                    phone = "+7 999 999 9999",
                    email = "ivan@ivanov.example"
            ))
            assertNotNull(obj.id)
            assertEquals("Ivan", obj.fname)
            assertEquals("Ivanovich", obj.mname)
            assertEquals("Ivanov", obj.lname)
            assertEquals(LocalDate.parse("2020-01-02"), obj.dob)
            assertEquals("+7 999 999 9999", obj.phone)
            assertEquals("ivan@ivanov.example", obj.email)
        }
    }

    @Test
    fun deleteTest() {
        runBlocking {
            val obj = repo.delete("delete-id")
            assertEquals("delete-id", obj.id)
            assertEquals("Ivan", obj.fname)
        }
    }

}
