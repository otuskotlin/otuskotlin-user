package ru.otus.otuskotlin.backend.repository.cassandra

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Session
import com.datastax.driver.core.Token
import com.datastax.driver.mapping.MappingManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.guava.await
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.COLUMN_BIRTH_DATE
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.COLUMN_CONTACTS_EMAIL
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.COLUMN_CONTACTS_PHONE
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.COLUMN_ID
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.COLUMN_NAME_FIRST
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.COLUMN_NAME_FULL
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.COLUMN_NAME_LAST
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.COLUMN_NAME_MIDDLE
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.TABLE_NAME
import ru.otus.otuskotlin.user.backend.common.exceptions.UserRepoNotFound
import ru.otus.otuskotlin.user.backend.common.exceptions.UserRepoWrongId
import ru.otus.otuskotlin.user.backend.common.models.UserIndexFilter
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import java.io.Closeable
import java.net.InetAddress
import java.time.Duration
import java.time.LocalDate
import java.util.*
import kotlin.coroutines.CoroutineContext


class UserRepositoryCassandra(
        private val keyspace: String,
        hosts: String = "",
        port: Int = 9042,
        user: String = "cassandra",
        pass: String = "cassandra",
        private val timeout: Duration = Duration.ofSeconds(10),
        private val searchParallelism: Int = 1,
        private val replicationFactor: Int = 1,
        initObjects: Collection<UserModel> = emptyList()
) : IUserRepository, CoroutineScope, Closeable {
    private val job = Job()

    private val cluster by lazy {
        Cluster.builder()
                .addContactPoints(parseAddresses(hosts))
                .withPort(port)
                .withCredentials(user, pass)
                .build()
    }

    private val session by lazy {
        runBlocking {
            createKeyspace()
            cluster.connect(keyspace).apply { createTable() }
        }
    }

    private val manager by lazy {
        MappingManager(session)
    }
    private val accessor by lazy {
        manager.createAccessor(UserCassandraAccessor::class.java)
    }

    private val mapper by lazy {
        val mpr = manager.mapper(UserCassandraDto::class.java, keyspace)
        runBlocking {
            initObjects
                    .map {
                        withTimeout(timeout.toMillis()) {
                            mpr.saveAsync(UserCassandraDto.of(it)).await()
                        }
                    }
        }
        mpr
    }

    override suspend fun get(id: String): UserModel {
        if (id.isBlank()) throw UserRepoWrongId(id)
        return withTimeout(timeout.toMillis()) {
            mapper.getAsync(id)?.await()?.toModel() ?: throw UserRepoNotFound(id)
        }
    }

    override suspend fun index(filter: UserIndexFilter): Collection<UserModel> = withTimeout(timeout.toMillis()) {
        val res = accessor
                .findByDob(LocalDate.parse(filter.dob))
                .await()
        res.all().map { it.toModel() }
    }

    override suspend fun create(user: UserModel): UserModel {
        val dto = UserCassandraDto.of(user, UUID.randomUUID().toString())
        return save(dto).toModel()
    }

    override suspend fun update(user: UserModel): UserModel {
        if (user.id.isBlank()) throw UserRepoWrongId(user.id)
        return save(UserCassandraDto.of(user)).toModel()
    }

    override suspend fun delete(id: String): UserModel {
        if (id.isBlank()) throw UserRepoWrongId(id)
        val obj: UserCassandraDto? = mapper.getAsync(id).await()
        if (obj != null) {
            mapper.deleteAsync(id).await()
            return obj.toModel()
        }
        throw UserRepoNotFound(id)
    }

    private suspend fun save(dto: UserCassandraDto): UserCassandraDto {
        withTimeout(timeout.toMillis()) { mapper.saveAsync(dto).await() }

        return withTimeout(timeout.toMillis()) { mapper.getAsync(dto.id).await() }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun close() {
        job.cancel()
    }

    private suspend fun createKeyspace() {
        cluster
                .connect()
                .executeAsync("""
            CREATE KEYSPACE IF NOT EXISTS $keyspace WITH REPLICATION = { 
                'class' : 'SimpleStrategy', 
                'replication_factor' : $replicationFactor
            }
        """.trimIndent()).await()
    }

    private suspend fun Session.createTable() {
        executeAsync("""
            CREATE TABLE IF NOT EXISTS $TABLE_NAME ( 
               $COLUMN_ID text,
               $COLUMN_NAME_FULL text,
               $COLUMN_NAME_FIRST text,
               $COLUMN_NAME_MIDDLE text,
               $COLUMN_NAME_LAST text,
               $COLUMN_CONTACTS_EMAIL text,
               $COLUMN_CONTACTS_PHONE text,
               $COLUMN_BIRTH_DATE date,
               
               PRIMARY KEY ($COLUMN_ID)
            )
       """.trimIndent()).await()
    }

    private fun parseAddresses(hosts: String): Collection<InetAddress> = hosts
            .split(Regex("""\s*,\s*"""))
//            .map { it.split(":") }
            .map { InetAddress.getByName(it) }


    fun init() {
        val mapper = mapper
    }
}
