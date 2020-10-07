package ru.otus.otuskotlin.user.backend.logics

import ru.otus.otuskotlin.common.cor.cor
import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.UserContextStatus
import ru.otus.otuskotlin.user.backend.common.errors.GeneralError
import ru.otus.otuskotlin.user.backend.common.models.UserCreateStubCases
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import ru.otus.otuskotlin.user.backend.common.models.UserPermissionsModel
import ru.otus.otuskotlin.user.backend.common.repositories.IUserRepository
import ru.otus.otuskotlin.user.backend.logics.handlers.responsePrepareHandler

class UserCreateChain(private val userRepo: IUserRepository) {

    suspend fun exec(context: UserContext) = chain.exec(context.apply {
        userRepo = this@UserCreateChain.userRepo
    })

    companion object {
        private val chain = cor<UserContext> {
            // Инициализация пайплайна
            exec { status = UserContextStatus.RUNNING }

            // Обработка стабов
            processor {
                isApplicable { stubCreateCase != UserCreateStubCases.NONE }
                handler {
                    isApplicable { stubCreateCase == UserCreateStubCases.SUCCESS }
                    exec {
                        responseUser = UserModel(
                                id = "test-create-id",
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
            handler {
                isApplicable { status == UserContextStatus.RUNNING }
                exec {
                    try {
                        responseUser = userRepo.create(requestUser)
                    } catch (e: Throwable) {
                        status = UserContextStatus.FAILING
                        errors.add(GeneralError(code = "repo-create-error", e = e))
                    }
                }
            }

            // Подготовка ответа
            exec(responsePrepareHandler)
        }
    }
}
