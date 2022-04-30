package com.nagy.assessment.common.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPhoto(

    @field:Json(name="albumId")
    val albumId: Long? = null,

    @field:Json(name="id")
    val id: Long? = null,

    @field:Json(name="title")
    val title: String? = null,

    @field:Json(name="url")
    val url: String? = null,

    @field:Json(name="thumbnailUrl")
    val thumbnailUrl: String? = null
)