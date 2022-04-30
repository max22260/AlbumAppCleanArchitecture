package com.nagy.assessment.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nagy.assessment.common.data.cache.daos.AlbumsDao
import com.nagy.assessment.common.data.cache.daos.PhotoDao
import com.nagy.assessment.common.data.cache.daos.UserDao
import com.nagy.assessment.common.data.cache.entities.CachedAlbum
import com.nagy.assessment.common.data.cache.entities.CachedPhoto
import com.nagy.assessment.common.data.cache.entities.CachedUser

@Database(
    entities = [
        CachedUser::class,
        CachedAlbum::class,
        CachedPhoto::class
    ],
    version = 1
)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun albumDao() : AlbumsDao
    abstract fun photoDao() : PhotoDao
}