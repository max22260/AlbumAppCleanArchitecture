package com.nagy.assessment.common.data.cache.daos

import androidx.room.*
import com.nagy.assessment.common.data.cache.entities.CachedAlbum
import com.nagy.assessment.common.data.cache.entities.CachedUser
import com.nagy.assessment.common.data.cache.entities.CachedUserWithAlbums
import io.reactivex.Flowable

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(cachedUser: CachedUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUserWithAlbums(
        user : CachedUser ,
        albums: List<CachedAlbum>
    )

    suspend fun insertUsers(users : List<CachedUser>) {
        for (user in users) {
            insertUser(user)
        }
    }

    suspend fun insertUsersWithAlbums(cachedUsersWithAlbums : List<CachedUserWithAlbums>) {
        for (user in cachedUsersWithAlbums) {
            insertUserWithAlbums(
                user.user,
                user.albums
            )
        }
    }

    @Transaction
    @Query("SELECT * FROM users ORDER BY userId DESC ")
    abstract fun getAllUsers() : Flowable<List<CachedUser>>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId ")
    abstract fun getUserWithAlbums(userId: Long) : Flowable<List<CachedUserWithAlbums>>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userid ")
    abstract suspend fun getUserById(userid : Long) : CachedUser

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userid ")
    abstract suspend fun getUserWithAlbumsById(userid : Long) : CachedUserWithAlbums


    @Transaction
    @Query("SELECT * FROM users ORDER BY random() LIMIT 1")
    abstract suspend fun getRandomUser() : CachedUserWithAlbums


}