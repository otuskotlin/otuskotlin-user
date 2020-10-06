package ru.otus.otuskotlin.backend.repository.inmemory

import org.cache2k.Cache
import org.cache2k.Cache2kBuilder
import ru.otus.otuskotlin.backend.repository.inmemory.exceptions.UserRepoNotFound
import ru.otus.otuskotlin.backend.repository.inmemory.exceptions.UserRepoWrongId
import ru.otus.otuskotlin.user.backend.common.models.UserIndexFilter
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime


class UserRepositoryInMemoty @OptIn(ExperimentalTime::class) constructor(ttl: Duration) : IUserRepository {
    @OptIn(ExperimentalTime::class)
    private var cache: Cache<String, UserInMemoryDto> = object : Cache2kBuilder<String, UserInMemoryDto>() {}
            .expireAfterWrite(ttl.toLongMilliseconds(), TimeUnit.MILLISECONDS) // expire/refresh after 5 minutes
            .suppressExceptions(false)
            .build()

    override fun get(id: String): UserModel {
        if (id.isBlank()) throw UserRepoWrongId(id)
        return cache.get(id)?.toModel() ?: throw UserRepoNotFound(id)
    }

    override fun index(filter: UserIndexFilter): Collection<UserModel> = cache.asMap()
            .filter {
                true
            }
            .map { it.value.toModel() }

    override fun create(user: UserModel): UserModel {
        val dto = UserInMemoryDto.of(user, UUID.randomUUID().toString())
        return save(dto).toModel()
    }

    override fun update(user: UserModel): UserModel {
        if (user.id.isBlank()) throw UserRepoWrongId(user.id)
        return save(UserInMemoryDto.of(user)).toModel()
    }

    override fun delete(id: String): UserModel {
        if (id.isBlank()) throw UserRepoWrongId(id)
        return cache.peekAndRemove(id)?.toModel() ?: throw UserRepoNotFound(id)
    }

    private fun save(dto: UserInMemoryDto): UserInMemoryDto {
        cache.put(dto.id, dto)
        return cache.get(dto.id)
    }
}
