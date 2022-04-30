package com.nagy.assessment.common.data.di

import android.content.Context
import androidx.room.Room
import com.nagy.assessment.common.data.cache.Cache
import com.nagy.assessment.common.data.cache.CacheDatabase
import com.nagy.assessment.common.data.cache.RoomCache
import com.nagy.assessment.common.data.cache.daos.AlbumsDao
import com.nagy.assessment.common.data.cache.daos.PhotoDao
import com.nagy.assessment.common.data.cache.daos.UserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): CacheDatabase {
            return Room.databaseBuilder(
                context,
                CacheDatabase::class.java,
                "Cache.db"
            )
                .build()
        }

        @Provides
        fun provideUserDao(
            cacheDatabase: CacheDatabase
        ): UserDao = cacheDatabase.userDao()

        @Provides
        fun provideAlbumDao(
            cacheDatabase: CacheDatabase
        ): AlbumsDao = cacheDatabase.albumDao()

        @Provides
        fun providePhotoDao(
            cacheDatabase: CacheDatabase
        ): PhotoDao = cacheDatabase.photoDao()


    }
}