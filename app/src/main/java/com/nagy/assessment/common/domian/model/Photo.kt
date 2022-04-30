package com.nagy.assessment.common.domian.model


data class Photo(

    val albumId: Long,

    val id: Long,

    val title: String,

    val url: String,

    val thumbnailUrl: String
) {

    companion object {
        private const val EMPTY_PHOTO = ""
    }

    private fun isValidPhoto(photo: String): Boolean {
        return photo.isNotEmpty()
    }

    fun getSmallestAvailablePhoto(): String {
        return when {
            isValidPhoto(thumbnailUrl) -> thumbnailUrl
            isValidPhoto(url) -> url
            else -> EMPTY_PHOTO
        }
    }
}
