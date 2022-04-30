package com.nagy.assessment.search.presentation.model.mappers

import com.nagy.assessment.common.domian.model.Photo
import com.nagy.assessment.common.presentation.model.mappers.UiMapper
import com.nagy.assessment.search.presentation.model.UiPhoto
import javax.inject.Inject

class UiPhotoMapper @Inject constructor() : UiMapper<Photo, UiPhoto> {
    override fun mapToView(input: Photo): UiPhoto {

        return UiPhoto(
            photoId = input.id,
            title = input.title ,
            fullSize = input.url ,
            smallSize = input.getSmallestAvailablePhoto()
        )
    }
}