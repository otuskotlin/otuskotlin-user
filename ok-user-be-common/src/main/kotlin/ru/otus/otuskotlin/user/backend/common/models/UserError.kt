package ru.otus.otuskotlin.user.backend.common.models

interface IUserError {

    val code: String
    val group: Groups
    val field: String
    val level: Levels
    val message: String

    enum class Levels(val weight: Int) {
        FATAL(90),
        ERROR(70),
        WARNING(40),
        INFO(20);

        val isError: Boolean
            get() = weight >= ERROR.weight

        val isWarning: Boolean
            get() = this == WARNING
    }

    enum class Groups(val alias: String) {

        /**
         * No errors
         */
        NONE(""),

        /**
         * Query received from the client contains errors or inconsistencies
         */
        VALIDATION("bad-query"),

        /**
         * Failure of other components of the system integration: database is down or other microservice connection failure
         */
        INTEGRATION("integration"),

        /**
         * Unknown/general server error
         */
        SERVER("internal-server"),

    }
}
