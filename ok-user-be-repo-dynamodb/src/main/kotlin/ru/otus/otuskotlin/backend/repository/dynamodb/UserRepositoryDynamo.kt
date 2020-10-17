package ru.otus.otuskotlin.backend.repository.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import io.kotless.dsl.lang.withKotlessLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withTimeout
import ru.otus.otuskotlin.backend.repository.dynamodb.UserDynamoDto.Companion.COLUMN_BIRTH_DATE
import ru.otus.otuskotlin.user.backend.common.exceptions.UserRepoNotFound
import ru.otus.otuskotlin.user.backend.common.exceptions.UserRepoWrongId
import ru.otus.otuskotlin.user.backend.common.models.UserIndexFilter
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import java.io.Closeable
import java.time.Duration
import java.util.*
import kotlin.coroutines.CoroutineContext


class UserRepositoryDynamo(
        private val timeout: Duration = Duration.ofSeconds(10)
) : IUserRepository, CoroutineScope, Closeable {
    private val job = Job()

    private val client by lazy {
        AmazonDynamoDBClientBuilder.standard().withKotlessLocal(io.kotless.AwsResource.DynamoDB).build()
    }
    private val mapper by lazy {
        DynamoDBMapper(client)
    }

    override suspend fun get(id: String): UserModel {
        if (id.isBlank()) throw UserRepoWrongId(id)
        return withTimeout(timeout.toMillis()) {
            mapper.load(UserDynamoDto::class.java, id)?.toModel() ?: throw UserRepoNotFound(id)
        }
    }

    override suspend fun index(filter: UserIndexFilter): Collection<UserModel> = withTimeout(timeout.toMillis()) {
        val queryExpression = DynamoDBScanExpression()
                .withFilterExpression("$COLUMN_BIRTH_DATE = :dob")
                .withExpressionAttributeValues(mapOf(
                        ":dob" to AttributeValue().withS(filter.dob)
                ))
        mapper.scan(UserDynamoDto::class.java, queryExpression)?.map { it.toModel() } ?: emptyList()
    }

    override suspend fun create(user: UserModel): UserModel {
        val dto = UserDynamoDto.of(user, UUID.randomUUID().toString())
        return save(dto).toModel()
    }

    override suspend fun update(user: UserModel): UserModel {
        if (user.id.isBlank()) throw UserRepoWrongId(user.id)
        return save(UserDynamoDto.of(user)).toModel()
    }

    override suspend fun delete(id: String): UserModel {
        if (id.isBlank()) throw UserRepoWrongId(id)
        val obj: UserDynamoDto? = mapper.load(UserDynamoDto::class.java, id)
        if (obj != null) {
            mapper.delete(id)
            return obj.toModel()
        }
        throw UserRepoNotFound(id)
    }

    private suspend fun save(dto: UserDynamoDto): UserDynamoDto {
        withTimeout(timeout.toMillis()) { mapper.save(dto) }

        return withTimeout(timeout.toMillis()) { mapper.load(UserDynamoDto::class.java, dto.id) }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun close() {
        job.cancel()
    }

}
