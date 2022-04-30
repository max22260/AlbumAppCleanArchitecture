package com.nagy.assessment.common.data.cache.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.nagy.assessment.common.domian.model.Album


@Entity(
    tableName = "albums",
    foreignKeys = [
        ForeignKey(
            entity = CachedUser::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class CachedAlbum(
    @PrimaryKey(autoGenerate = false)
    val albumId: Long,

    val title: String,

    val userId: Long
) {
    companion object {

        fun fromDomain(domainAlbum: Album): CachedAlbum {

            return CachedAlbum(
                albumId = domainAlbum.id,
                title = domainAlbum.title,
                userId = domainAlbum.userId
            )
        }

        fun toDomain(cachedAlbum: CachedAlbum): Album {

            return Album(
                id = cachedAlbum.albumId,
                title = cachedAlbum.title,
                userId = cachedAlbum.userId
                )
        }
    }


}