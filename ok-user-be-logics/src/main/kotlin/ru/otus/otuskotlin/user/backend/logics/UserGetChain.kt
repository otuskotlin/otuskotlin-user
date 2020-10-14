package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.errors.GeneralError
import ru.otus.otuskotlin.user.backend.common.models.UserGetStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import ru.otus.otuskotlin.user.backend.logics.handlers.querySetWorkMode
import ru.otus.otuskotlin.user.backend.logics.handlers.responsePrepareHandler
import ru.otus.otuskotlin.user.backend.logics.handlers.stubsGet
import java.time.LocalDate

class UserGetChain(
        private val userRepoProd: IUserRepository,
        private val userRepoTest: IUserRepository
) {

    suspend fun exec(context: UserContext) = UserGetChain.chain.exec(context.apply {
        userRepoProd = this@UserGetChain.userRepoProd
        userRepoTest = this@UserGetChain.userRepoTest
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

            exec(stubsGet) // Обработка стабов

            // Обработка и работа с БД
            handler {
                isApplicable { status == UserContextStatus.RUNNING }
                exec {
                    try {
                        responseUser = userRepo.get(requestUserId)
                    } catch (e: Throwable) {
                        status = UserContextStatus.FAILING
                        errors.add(GeneralError(code = "repo-get-error", e = e))
                    }
                }
            }

            // Подготовка ответа
            // Подготовка ответа
            exec(responsePrepareHandler)
        }
    }
}
