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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Delete
    suspend fun delete(post: PostEntity)
}