package ru.otus.otuskotlin.user.backend.common.dsl

import ru.otus.otuskotlin.user.backend.common.UserContext
import ru.otus.otuskotlin.user.backend.common.models.UserModel
import kotlin.math.pow


infix fun Double.powerOf(power: Double) = this.pow(power)

infix fun UserContext.applyRequest(user: UserModel) = this.apply { requestUser = user }
