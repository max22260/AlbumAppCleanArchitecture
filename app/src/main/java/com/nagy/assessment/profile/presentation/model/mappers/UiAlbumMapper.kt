package com.nagy.assessment.profile.presentation.model.mappers

import com.nagy.assessment.common.domian.model.Album
import com.nagy.assessment.common.presentation.model.mappers.UiMapper
import com.nagy.assessment.profile.presentation.model.UiAlbum
import javax.inject.Inject

class UiAlbumMapper @Inject constructor() : UiMapper<Album, UiAlbum> {
    override fun mapToView(input: Album): UiAlbum {

        return UiAlbum(
            albumId = input.id,
            title = input.title
        )
    }
}