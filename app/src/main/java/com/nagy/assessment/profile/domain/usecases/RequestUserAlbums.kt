package com.nagy.assessment.profile.domain.usecases

import com.nagy.assessment.common.domian.model.NoAlbumsException
import com.nagy.assessment.common.domian.model.NoUsersException
import com.nagy.assessment.common.domian.repository.Repository
import javax.inject.Inject

class RequestUserAlbums @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(userId: Int) {

        val albums = repository.requestAlbums(userId)

        if (albums.isEmpty()) {

            throw NoAlbumsException("no albums found for this user ")
        }

        repository.storeAlbums(albums)
    }
}