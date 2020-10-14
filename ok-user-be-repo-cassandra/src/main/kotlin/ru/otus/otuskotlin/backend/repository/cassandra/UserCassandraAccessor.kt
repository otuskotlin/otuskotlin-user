package ru.otus.otuskotlin.backend.repository.cassandra

import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec
import com.datastax.driver.mapping.Result
import com.datastax.driver.mapping.annotations.Accessor
import com.datastax.driver.mapping.annotations.Param
import com.datastax.driver.mapping.annotations.Query
import com.google.common.util.concurrent.ListenableFuture
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.COLUMN_BIRTH_DATE
import ru.otus.otuskotlin.backend.repository.cassandra.UserCassandraDto.Companion.TABLE_NAME
import java.time.LocalDate

@Accessor
interface UserCassandraAccessor {
    @Query(QUERY_FIND_BY_DOB)
    fun findByDob(
            @Param("dob", codec = LocalDateCodec::class) dob: LocalDate,
    ): ListenableFuture<Result<UserCassandraDto>>

    companion object {
        const val QUERY_FIND_BY_DOB = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_BIRTH_DATE=:dob ALLOW FILTERING"
    }
}
