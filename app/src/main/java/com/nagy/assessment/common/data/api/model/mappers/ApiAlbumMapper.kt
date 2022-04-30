package com.nagy.assessment.common.data.api.model.mappers

import com.nagy.assessment.common.data.api.model.ApiAlbum
import com.nagy.assessment.common.domian.model.Album
import javax.inject.Inject

class ApiAlbumMapper @Inject constructor() : ApiMapper <ApiAlbum? , Album> {

    override fun mapToDomain(apiEntity: ApiAlbum?): Album {

        return Album(
            userId = apiEntity?.userId ?: throw MappingException("userId cannot be null"),
            id =  apiEntity.id ?: throw MappingException("userId cannot be null"),
            title = apiEntity.title.orEmpty()
        )
    }
}