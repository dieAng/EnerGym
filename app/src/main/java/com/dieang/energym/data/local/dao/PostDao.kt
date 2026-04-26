package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.data.local.entity.PostEntity
import java.util.UUID

@Dao
interface PostDao {

    @Query("SELECT * FROM post ORDER BY fecha DESC")
    suspend fun getAll(): List<PostEntity>

    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getById(id: UUID): PostEntity?

    @Query("SELECT * FROM post WHERE sincronizado = 0")
    suspend fun getNoSincronizados(): List<PostEntity>

    @Query("DELETE FROM post WHERE sincronizado = 1 AND fecha < :timestamp")
    suspend fun deleteOldSynchronized(timestamp: Long)

    @Query("DELETE FROM post WHERE id NOT IN (SELECT id FROM post ORDER BY fecha DESC LIMIT :limit)")
    suspend fun keepOnlyLatest(limit: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Delete
    suspend fun delete(post: PostEntity)
}