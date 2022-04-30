package com.nagy.assessment.profile.domain.usecases

import com.nagy.assessment.common.domian.repository.Repository
import javax.inject.Inject

class GetUserWithAlbums @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(userId : Long) = repository.getSpecificUserWithAlbums(userId)
}