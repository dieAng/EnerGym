package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.data.local.entity.PostEntity
import com.dieang.energym.domain.model.Post
import java.util.UUID

@Dao
interface PostDao {

    @Query("SELECT * FROM post ORDER BY fecha DESC")
    suspend fun getAll(): List<Post>

    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getById(id: UUID): Post?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Delete
    suspend fun delete(post: Post)
}