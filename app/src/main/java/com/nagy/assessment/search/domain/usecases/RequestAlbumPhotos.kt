package com.nagy.assessment.search.domain.usecases

import com.nagy.assessment.common.domian.model.NoAlbumsException
import com.nagy.assessment.common.domian.model.NoPhotosException
import com.nagy.assessment.common.domian.repository.Repository
import javax.inject.Inject

class RequestAlbumPhotos  @Inject constructor(private val repository: Repository){

    suspend operator fun invoke( albumId : Int){
       val photos = repository.requestPhotos(albumId)

        if (photos.isEmpty()) {
            throw NoPhotosException("no photos found in this Album ")
        }
        repository.storePhotos(photos)
    }
}