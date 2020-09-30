package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserDeleteStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserGetStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import java.time.LocalDate

class UserDeleteChain {

    suspend fun exec(context: UserContext) = chain.exec(context.apply {

    })

    companion object {
        private val chain = cor<UserContext> {
            // Инициализация пайплайна
            exec { status = UserContextStatus.RUNNING }

            // Обработка стабов
            processor {
                isApplicable { stubDeleteCase != UserDeleteStubCases.NONE }
                handler {
                    isApplicable { stubDeleteCase == UserDeleteStubCases.SUCCESS }
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

            // Подготовка ответа
            exec {
                status = UserContextStatus.SUCCESS
            }
        }
    }
}
