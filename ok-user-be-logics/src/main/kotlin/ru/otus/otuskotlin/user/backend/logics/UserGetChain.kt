package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.errors.GeneralError
import ru.otus.otuskotlin.user.backend.common.models.UserGetStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import ru.otus.otuskotlin.user.backend.logics.handlers.responsePrepareHandler
import java.time.LocalDate

class UserGetChain(private val userRepo: IUserRepository) {

    suspend fun exec(context: UserContext) = chain.exec(context.apply {
        userRepo = this@UserGetChain.userRepo
    })

    companion object {
        private val chain = cor<UserContext> {
            // Инициализация пайплайна
            exec { status = UserContextStatus.RUNNING }

            // Обработка стабов
            processor {
                isApplicable { stubGetCase != UserGetStubCases.NONE }
                handler {
                    isApplicable { stubGetCase == UserGetStubCases.SUCCESS }
                    exec {
                        responseUser = UserModel(
                                id = requestUserId,
                                fname = "Ivan",
                                mname = "Ivanovich",
                                lname = "Ivanov",
                                dob = LocalDate.parse("2000-01-01"),
                                email = "ivan@ivanov.example",
                                phone = "+7 999 999 9999",
                                permissions = mutableSetOf(
                                        UserPermissionsModel.SEND_MESSAGE,
                                        UserPermissionsModel.UPDATE,
                                        UserPermissionsModel.GET_NEWS,
                                        UserPermissionsModel.VIEW
                                )
                        )
                        status = UserContextStatus.FINISHING
                    }
                }
            }

            // Валидация

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