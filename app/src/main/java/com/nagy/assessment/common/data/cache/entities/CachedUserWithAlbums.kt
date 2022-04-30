package com.nagy.assessment.common.data.cache.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.nagy.assessment.common.domian.model.UserWithAlbum

data class CachedUserWithAlbums(
    @Embedded
    val user: CachedUser ,

    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val albums : List<CachedAlbum>
) {

    companion object {

        fun fromDomain(domainUserWithAlbums: UserWithAlbum) : CachedUserWithAlbums {

            return CachedUserWithAlbums(
                user = CachedUser.fromDomain(domainUserWithAlbums.user),
                albums = domainUserWithAlbums.Albums.map { CachedAlbum.fromDomain(it) }
            )
        }


        fun toDomain(cachedUserWithAlbums: CachedUserWithAlbums) : UserWithAlbum {
            return UserWithAlbum(
                user = CachedUser.toDomain(cachedUserWithAlbums.user) ,
                Albums = cachedUserWithAlbums.albums.map { CachedAlbum.toDomain(it) }
            )
        }
    }
}