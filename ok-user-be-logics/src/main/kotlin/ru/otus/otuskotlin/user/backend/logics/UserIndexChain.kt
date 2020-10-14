package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.errors.GeneralError
import ru.otus.otuskotlin.user.backend.common.models.UserIndexStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import ru.otus.otuskotlin.user.backend.logics.handlers.querySetWorkMode
import ru.otus.otuskotlin.user.backend.logics.handlers.responsePrepareHandler
import ru.otus.otuskotlin.user.backend.logics.handlers.stubIndex
import java.time.LocalDate

class UserIndexChain(
        private val userRepoProd: IUserRepository,
        private val userRepoTest: IUserRepository
) {

    suspend fun exec(context: UserContext) = UserIndexChain.chain.exec(context.apply {
        userRepoProd = this@UserIndexChain.userRepoProd
        userRepoTest = this@UserIndexChain.userRepoTest
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
            exec(stubIndex)

            // Обработка и работа с БД
            handler {
                isApplicable { status == UserContextStatus.RUNNING }
                exec {
                    try {
                        responseUsers = userRepo.index(requestUserFilter).toMutableList()
                    } catch (e: Throwable) {
                        status = UserContextStatus.FAILING
                        errors.add(GeneralError(code = "repo-index-error", e = e))
                    }
                }
            }

            // Подготовка ответа
            exec(responsePrepareHandler)
        }
    }
}
