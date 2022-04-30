package com.nagy.assessment.common.data.cache

import com.nagy.assessment.common.data.cache.daos.AlbumsDao
import com.nagy.assessment.common.data.cache.daos.PhotoDao
import com.nagy.assessment.common.data.cache.daos.UserDao
import com.nagy.assessment.common.data.cache.entities.CachedAlbum
import com.nagy.assessment.common.data.cache.entities.CachedPhoto
import com.nagy.assessment.common.data.cache.entities.CachedUser
import com.nagy.assessment.common.data.cache.entities.CachedUserWithAlbums
import io.reactivex.Flowable
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val userDao: UserDao ,
    private val albumsDao: AlbumsDao,
    private val photoDao: PhotoDao
) : Cache {
    override suspend fun storeUsers(users: List<CachedUser>) {
        userDao.insertUsers(users)
    }

    override suspend fun storeUsersWithAlbums(users: List<CachedUserWithAlbums>) {
        userDao.insertUsersWithAlbums(users)
    }

    override suspend fun storeAlbums(albums: List<CachedAlbum>) {
        albumsDao.insertAllAlbums(albums)
    }

    override suspend fun storePhotos(photos: List<CachedPhoto>) {
        photoDao.insertAllPhotos(photos)
    }

    override fun getUsers(): Flowable<List<CachedUser>> {
        return userDao.getAllUsers()
    }

    override fun getUserWithAlbums(userId: Long): Flowable<List<CachedUserWithAlbums>> {
       return userDao.getUserWithAlbums(userId)
    }

    override fun getSpecificAlbums(userId: Long): Flowable<List<CachedAlbum>> {

        return albumsDao.getAllAlbumsByUserId(userId)
    }

    override fun getSpecificPhotos(albumId: Long): Flowable<List<CachedPhoto>> {
        return photoDao.getAllPhotosByAlbumId(albumId)
    }

    override suspend fun getSpecificUser(userId: Long) : CachedUser {
        return userDao.getUserById(userId)
    }

    override suspend fun getSpecificUserWithAlbums(userId: Long): CachedUserWithAlbums {
        return userDao.getUserWithAlbumsById(userId)
    }

    override suspend fun getRandomUser(): CachedUserWithAlbums {
       return userDao.getRandomUser()
    }

    override fun searchPhotoBy(albumId: Long, title: String): Flowable<List<CachedPhoto>> {
       return photoDao.searchPhotoByTitle(albumId, title)
    }
}