package com.nagy.assessment.profile.domain.usecases

import com.nagy.assessment.common.domian.repository.Repository
import javax.inject.Inject

class GetUserAlbums @Inject constructor(private val repository: Repository) {

    operator fun invoke(userId : Long) = repository.getSpecificAlbums(userId)
        .filter { it.isNotEmpty() }
}