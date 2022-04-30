package com.nagy.assessment.common.data.api.model.mappers

import com.nagy.assessment.common.data.api.model.ApiPhoto
import com.nagy.assessment.common.domian.model.Photo
import javax.inject.Inject

class ApiPhotoMapper @Inject constructor() : ApiMapper<ApiPhoto , Photo> {

    override fun mapToDomain(apiEntity: ApiPhoto): Photo {
        return Photo(
            albumId = apiEntity.albumId ?: throw MappingException("album Id cannot be null") ,
            id =  apiEntity.id?: throw MappingException("photo Id cannot be null") ,
            title = apiEntity.title.orEmpty(),
            url = apiEntity.url.orEmpty(),
            thumbnailUrl = apiEntity.thumbnailUrl.orEmpty()
        )
    }
}