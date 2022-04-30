package com.nagy.assessment.profile.domain.usecases

import com.nagy.assessment.common.domian.repository.Repository
import javax.inject.Inject

class GetUsers @Inject constructor(private val repository: Repository) {

    operator fun invoke() = repository.getUsers()
        .filter { it.isNotEmpty() }

}

