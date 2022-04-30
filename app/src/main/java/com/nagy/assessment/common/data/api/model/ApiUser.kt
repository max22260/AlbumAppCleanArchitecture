package com.nagy.assessment.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUser(

    @field:Json(name="website")
    val website: String? = null,

    @field:Json(name="address")
    val address: ApiAddress? = null,

    @field:Json(name="phone")
    val phone: String? = null,

    @field:Json(name="name")
    val name: String? = null,

    @field:Json(name="company")
    val company: ApiCompany? = null,

    @field:Json(name="id")
    val id: Long? = null,

    @field:Json(name="email")
    val email: String? = null,

    @field:Json(name="username")
    val username: String? = null
)

@JsonClass(generateAdapter = true)
data class ApiAddress(

    @field:Json(name="zipcode")
    val zipcode: String? = null,

    @field:Json(name="geo")
    val geo: ApiGeo? = null,

    @field:Json(name="suite")
    val suite: String? = null,

    @field:Json(name="city")
    val city: String? = null,

    @field:Json(name="street")
    val street: String? = null
)

@JsonClass(generateAdapter = true)
data class ApiGeo(

    @field:Json(name="lng")
    val lng: String? = null,

    @field:Json(name="lat")
    val lat: String? = null
)

@JsonClass(generateAdapter = true)
data class ApiCompany(

    @field:Json(name="bs")
    val bs: String? = null,

    @field:Json(name="catchPhrase")
    val catchPhrase: String? = null,

    @field:Json(name="name")
    val name: String? = null
)