package com.nagy.assessment.common.data

import com.nagy.assessment.common.data.api.Api
import com.nagy.assessment.common.data.api.model.mappers.ApiAlbumMapper
import com.nagy.assessment.common.data.api.model.mappers.ApiPhotoMapper
import com.nagy.assessment.common.data.api.model.mappers.ApiUserMapper
import com.nagy.assessment.common.data.cache.Cache
import com.nagy.assessment.common.data.cache.entities.CachedAlbum
import com.nagy.assessment.common.data.cache.entities.CachedPhoto
import com.nagy.assessment.common.data.cache.entities.CachedUser
import com.nagy.assessment.common.data.cache.entities.CachedUserWithAlbums
import com.nagy.assessment.common.domian.model.*
import com.nagy.assessment.common.domian.repository.Repository
import io.reactivex.Flowable
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val api: Api,
    private val cache: Cache,
    private val apiUserMapper: ApiUserMapper,
    private val apiAlbumMapper: ApiAlbumMapper,
    private val apiPhotoMapper: ApiPhotoMapper
) : Repository {

    override suspend fun requestUsers(): List<User> {

        try {

            val users = api.getUsers()
            return users.map { apiUserMapper.mapToDomain(it) }

        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")

        }
    }

    override suspend fun requestAlbums(userId: Int): List<Album> {

        try {
            val albums = api.getAlbums(userId)
            return albums.map { apiAlbumMapper.mapToDomain(it) }

        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestPhotos(albumId: Int): List<Photo> {
        try {
            val photos = api.getPhotos(albumId)
            return photos.map { apiPhotoMapper.mapToDomain(it) }

        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storeUsers(users: List<User>) {

        val cacheUsers = users.map { CachedUser.fromDomain(it) }

        cache.storeUsers(cacheUsers)
    }

    override suspend fun storeUsersWithAlbums(users: List<UserWithAlbum>) {

        val userWithAlbum = users.map { CachedUserWithAlbums.fromDomain(it) }

        cache.storeUsersWithAlbums(userWithAlbum)

    }

    override suspend fun storeAlbums(albums: List<Album>) {

        val cachedAlbums = albums.map { CachedAlbum.fromDomain(it) }

        cache.storeAlbums(cachedAlbums)

    }

    override suspend fun storePhotos(photos: List<Photo>) {

        val cachedPhoto = photos.map { CachedPhoto.fromDomain(it) }

        cache.storePhotos(cachedPhoto)
    }

    override fun getUsers(): Flowable<List<User>> {

        return cache.getUsers()
            .distinctUntilChanged()
            .map { userList -> userList.map { CachedUser.toDomain(it) } }
    }

    override fun getUsersWithAlbums(userId: Long): Flowable<List<UserWithAlbum>> {

        return cache.getUserWithAlbums(userId)
            .distinctUntilChanged()
            .map { userWithAlbumsList -> userWithAlbumsList.map { CachedUserWithAlbums.toDomain(it) } }

    }

    override fun getSpecificAlbums(userId: Long): Flowable<List<Album>> {

        return cache.getSpecificAlbums(userId)
            .distinctUntilChanged()
            .map { albumList -> albumList.map { CachedAlbum.toDomain(it) } }
    }

    override fun getAlbumPhotos(albumId: Long): Flowable<List<Photo>> {

        return cache.getSpecificPhotos(albumId)
            .distinctUntilChanged()
            .map { photoList -> photoList.map { CachedPhoto.toDomain(it) } }

    }

    override suspend fun getSpecificUser(userId: Long): User {

        return CachedUser.toDomain(cache.getSpecificUser(userId))
    }

    override suspend fun getSpecificUserWithAlbums(userId: Long): UserWithAlbum {

        return  CachedUserWithAlbums.toDomain(cache.getSpecificUserWithAlbums(userId))
    }

    override fun searchPhotoBy(albumId: Long, title: String): Flowable<List<Photo>> {

        return cache.searchPhotoBy(albumId,title)
            .distinctUntilChanged()
            .map { photoList -> photoList.map { CachedPhoto.toDomain(it) } }
    }
}