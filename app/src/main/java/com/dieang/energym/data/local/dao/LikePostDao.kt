package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.data.local.entity.LikePostEntity
import java.util.UUID

@Dao
interface LikePostDao {

    @Query("SELECT * FROM like_post WHERE postId = :postId")
    suspend fun getLikes(postId: UUID): List<LikePostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(like: LikePostEntity)

    @Delete
    suspend fun delete(like: LikePostEntity)
}