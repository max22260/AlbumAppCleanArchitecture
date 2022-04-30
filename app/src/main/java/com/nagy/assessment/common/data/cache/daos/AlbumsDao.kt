package com.nagy.assessment.common.data.cache.daos

import androidx.room.*
import com.nagy.assessment.common.data.cache.entities.CachedAlbum
import io.reactivex.Flowable


@Dao
abstract class AlbumsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAlbum(cachedAlbum: CachedAlbum)

    suspend fun insertAllAlbums(cachedAlbums: List<CachedAlbum>) {
        for (album in cachedAlbums) {
            insertAlbum(album)
        }
    }

    @Transaction
    @Query("SELECT * FROM albums WHERE userId = :userId  ORDER BY albumId DESC")
    abstract fun getAllAlbumsByUserId(userId : Long)  : Flowable<List<CachedAlbum>>


}