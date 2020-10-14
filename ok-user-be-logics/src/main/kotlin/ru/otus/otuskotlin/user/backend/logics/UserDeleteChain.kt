package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.errors.GeneralError
import ru.otus.otuskotlin.user.backend.common.models.UserDeleteStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.backend.common.models.WorkModes
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import ru.otus.otuskotlin.user.backend.logics.handlers.querySetWorkMode
import ru.otus.otuskotlin.user.backend.logics.handlers.responsePrepareHandler
import ru.otus.otuskotlin.user.backend.logics.handlers.stubDelete
import java.time.LocalDate

class UserDeleteChain(
        private val userRepoProd: IUserRepository,
        private val userRepoTest: IUserRepository
) {

    suspend fun exec(context: UserContext) = chain.exec(context.apply {
        userRepoProd = this@UserDeleteChain.userRepoProd
        userRepoTest = this@UserDeleteChain.userRepoTest
    })

    companion object {
        private val chain = cor<UserContext> {
            // Инициализация пайплайна
            exec { status = UserContextStatus.RUNNING }

            // Валидация
            // Обработка запроса
            processor {
                exec(querySetWorkMode)
            }

            // Обработка стабов
            exec(stubDelete)

            // Обработка и работа с БД
            handler {
                isApplicable { status == UserContextStatus.RUNNING }
                exec {
                        responseUser = userRepo.delete(requestUserId)
                }
                onError { e ->
                    status = UserContextStatus.FAILING
                    errors.add(GeneralError(code = "repo-delete-error", e = e))
                }
            }

            // Подготовка ответа
            exec(responsePrepareHandler)
        }
    }
}
