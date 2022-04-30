package com.nagy.assessment.common.data.api

import com.nagy.assessment.common.data.api.model.ApiAlbum
import com.nagy.assessment.common.data.api.model.ApiPhoto
import com.nagy.assessment.common.data.api.model.ApiUser
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(ApiConstants.USERS_ENDPOINT)
    suspend fun getUsers() : List<ApiUser>

    @GET(ApiConstants.ALBUMS_ENDPOINT)
    suspend fun getAlbums( @Query(ApiParameters.USER_ID) userId : Int) : List<ApiAlbum>

    @GET(ApiConstants.PHOTOS_ENDPOINT)
    suspend fun getPhotos(@Query(ApiParameters.ALBUM_ID) albumId : Int) : List<ApiPhoto>
    
}