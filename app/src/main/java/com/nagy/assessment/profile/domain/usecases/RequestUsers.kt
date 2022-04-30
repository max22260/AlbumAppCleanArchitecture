package com.nagy.assessment.profile.domain.usecases

import com.nagy.assessment.common.domian.model.NoUsersException
import com.nagy.assessment.common.domian.model.User
import com.nagy.assessment.common.domian.repository.Repository
import javax.inject.Inject

class RequestUsers @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke() : User {

        val users = repository.requestUsers()

        if (users.isEmpty()) {

            throw NoUsersException("no Users on server ")
        }

        repository.storeUsers(users)

        return users.random()
    }

}