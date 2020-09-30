package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.models.UserUpdateStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import java.time.LocalDate

class UserUpdateChain {

    suspend fun exec(context: UserContext) = chain.exec(context.apply {

    })

    companion object {
        private val chain = cor<UserContext> {
            // Инициализация пайплайна
            exec { status = UserContextStatus.RUNNING }

            // Обработка стабов
            processor {
                isApplicable { stubUpdateCase != UserUpdateStubCases.NONE }
                handler {
                    isApplicable { stubUpdateCase == UserUpdateStubCases.SUCCESS }
                    exec {
                        responseUser = UserModel(
                                id = requestUser.id,
                                fname = requestUser.fname,
                                mname = requestUser.mname,
                                lname = requestUser.lname,
                                dob = requestUser.dob,
                                email = requestUser.email,
                                phone = requestUser.phone,
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
