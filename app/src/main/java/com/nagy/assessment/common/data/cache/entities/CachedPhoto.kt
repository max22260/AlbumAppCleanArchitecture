package com.nagy.assessment.common.data.cache.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.nagy.assessment.common.domian.model.Photo


@Entity(
    tableName = "photos",
    foreignKeys = [
        ForeignKey(
            entity = CachedAlbum::class,
            parentColumns = ["albumId"],
            childColumns = ["albumId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index("albumId")]
)
data class CachedPhoto(


    @PrimaryKey(autoGenerate = false)
    val photoId: Long,

    val albumId: Long,

    val title: String,

    val url: String,

    val thumbnailUrl: String
) {

    companion object {

        fun fromDomain(domainPhoto: Photo): CachedPhoto {

            return CachedPhoto(
                photoId = domainPhoto.id,
                albumId = domainPhoto.albumId,
                title = domainPhoto.title,
                url = domainPhoto.url,
                thumbnailUrl = domainPhoto.thumbnailUrl
            )
        }

        fun toDomain(cachedPhoto: CachedPhoto): Photo {

            return Photo(
                id = cachedPhoto.photoId,
                albumId = cachedPhoto.albumId,
                title = cachedPhoto.title,
                url = cachedPhoto.url,
                thumbnailUrl = cachedPhoto.thumbnailUrl
            )
        }
    }

}