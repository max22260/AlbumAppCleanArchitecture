package com.nagy.assessment.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiAlbum (

    @field:Json(name = "id")
    val id: Long? = null,

    @field:Json(name="title")
    val title: String? = null,

    @field:Json(name="userId")
    val userId: Long? = null
)