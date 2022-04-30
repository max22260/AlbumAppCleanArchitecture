package com.nagy.assessment.common.data.cache

import com.nagy.assessment.common.data.cache.entities.CachedAlbum
import com.nagy.assessment.common.data.cache.entities.CachedPhoto
import com.nagy.assessment.common.data.cache.entities.CachedUser
import com.nagy.assessment.common.data.cache.entities.CachedUserWithAlbums
import io.reactivex.Flowable

interface Cache {

    suspend fun storeUsers(users : List<CachedUser>)
    suspend fun storeUsersWithAlbums(users : List<CachedUserWithAlbums>)
    suspend fun storeAlbums(albums: List<CachedAlbum>)
    suspend fun storePhotos(photos : List<CachedPhoto>)

    fun getUsers() : Flowable<List<CachedUser>>
    fun getUserWithAlbums(userId: Long) : Flowable<List<CachedUserWithAlbums>>
    fun getSpecificAlbums(userId : Long)  :Flowable<List<CachedAlbum>>
    fun getSpecificPhotos(albumId : Long)  :Flowable<List<CachedPhoto>>
    suspend fun getSpecificUser(userId : Long) : CachedUser
    suspend fun getSpecificUserWithAlbums(userId: Long) : CachedUserWithAlbums
    suspend fun getRandomUser(): CachedUserWithAlbums

    fun searchPhotoBy(
        albumId: Long,
        title: String,
    ): Flowable<List<CachedPhoto>>

}