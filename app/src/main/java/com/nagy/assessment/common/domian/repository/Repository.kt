package com.nagy.assessment.common.domian.repository

import com.nagy.assessment.common.data.cache.entities.CachedAlbum
import com.nagy.assessment.common.data.cache.entities.CachedPhoto
import com.nagy.assessment.common.data.cache.entities.CachedUser
import com.nagy.assessment.common.data.cache.entities.CachedUserWithAlbums
import com.nagy.assessment.common.domian.model.Album
import com.nagy.assessment.common.domian.model.Photo
import com.nagy.assessment.common.domian.model.User
import com.nagy.assessment.common.domian.model.UserWithAlbum
import io.reactivex.Flowable

interface Repository {

    suspend fun requestUsers(): List<User>
    suspend fun requestAlbums(userId: Int): List<Album>
    suspend fun requestPhotos(albumId: Int): List<Photo>


    suspend fun storeUsers(users: List<User>)
    suspend fun storeUsersWithAlbums(users: List<UserWithAlbum>)
    suspend fun storeAlbums(albums: List<Album>)
    suspend fun storePhotos(photos: List<Photo>)

    fun getUsers(): Flowable<List<User>>
    fun getUsersWithAlbums(userId: Long): Flowable<List<UserWithAlbum>>
    fun getSpecificAlbums(userId: Long): Flowable<List<Album>>
    fun getAlbumPhotos(albumId: Long): Flowable<List<Photo>>
    suspend fun getSpecificUser(userId: Long): User
    suspend fun getSpecificUserWithAlbums(userId: Long): UserWithAlbum
    fun searchPhotoBy(albumId: Long, title: String): Flowable<List<Photo>>


}