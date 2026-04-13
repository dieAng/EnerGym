package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.data.local.entity.ComentarioEntity
import java.util.UUID

@Dao
interface ComentarioDao {

    @Query("SELECT * FROM comentario WHERE postId = :postId ORDER BY fecha ASC")
    suspend fun getByPost(postId: UUID): List<ComentarioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comentario: ComentarioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(series: List<ComentarioEntity>)

    @Delete
    suspend fun delete(comentario: ComentarioEntity)
}