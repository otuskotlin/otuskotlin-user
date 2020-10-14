package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.errors.GeneralError
import ru.otus.otuskotlin.user.backend.common.models.UserUpdateStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import ru.otus.otuskotlin.user.backend.logics.handlers.querySetWorkMode
import ru.otus.otuskotlin.user.backend.logics.handlers.responsePrepareHandler
import ru.otus.otuskotlin.user.backend.logics.handlers.stubUpdate

class UserUpdateChain(
        private val userRepoProd: IUserRepository,
        private val userRepoTest: IUserRepository
) {

    suspend fun exec(context: UserContext) = chain.exec(context.apply {
        userRepoProd = this@UserUpdateChain.userRepoProd
        userRepoTest = this@UserUpdateChain.userRepoTest
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
            exec(stubUpdate)

            // Обработка и работа с БД
            handler {
                isApplicable { status == UserContextStatus.RUNNING }
                exec {
                    try {
                        responseUser = userRepo.update(requestUser)
                    } catch (e: Throwable) {
                        status = UserContextStatus.FAILING
                        errors.add(GeneralError(code = "repo-update-error", e = e))
                    }
                }
            }

            // Подготовка ответа
            exec(responsePrepareHandler)
        }
    }
}
