package ru.otus.otuskotlin.user.transport.openapi.api

import org.springframework.stereotype.Service
import ru.otus.otuskotlin.user.transport.openapi.model.*

@Service
class UserApiServiceImpl : UserApiService {

    override fun userCreate(body: OaUserQueryCreate): OaUserResponseItem {
        TODO("Implement me")
    }

    override fun userDelete(body: OaUserQueryDelete): OaUserResponseItem {
        TODO("Implement me")
    }

    override fun userGet(body: OaUserQueryGet): OaUserResponseItem {
        TODO("Implement me")
    }

    override fun userIndex(body: OaUserQueryIndex): OaUserResponseIndex = OaUserResponseIndex(
            status = OaUserResponseStatus.SUCCESS,
            data = listOf(
                    OaUser(
                            id = "some-id-1",
                            fname = "FirstName",
                            mname = "MiddleName",
                            lname = "LastName",
                            dob = "2000-01-01"
                    )
            )
    )

    override fun userUpdate(body: OaUserQueryUpdate): OaUserResponseItem {
        TODO("Implement me")
    }
}
