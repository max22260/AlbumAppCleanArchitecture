package com.nagy.assessment.common.data.cache.daos

import androidx.room.*
import com.nagy.assessment.common.data.cache.entities.CachedPhoto
import io.reactivex.Flowable

@Dao
abstract class PhotoDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPhoto(cachedPhoto: CachedPhoto)

    suspend fun insertAllPhotos(cachedPhoto: List<CachedPhoto>) {
        for (photo in cachedPhoto) {
            insertPhoto(photo)
        }
    }

    @Transaction
    @Query("SELECT * FROM photos WHERE albumId = :albumId   ORDER BY photoId DESC")
    abstract fun getAllPhotosByAlbumId(albumId: Long): Flowable<List<CachedPhoto>>

    @Transaction
    @Query(
        """
    SELECT * FROM photos 
    WHERE title LIKE '%' || :title || '%' AND albumId = :albumId 
    """
    )
    abstract fun searchPhotoByTitle(
        albumId: Long,
        title: String,
    ): Flowable<List<CachedPhoto>>

}