package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.domain.model.Comentario
import java.util.UUID

@Dao
interface ComentarioDao {

    @Query("SELECT * FROM comentario WHERE postId = :postId ORDER BY fecha ASC")
    suspend fun getByPost(postId: UUID): List<Comentario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comentario: Comentario)

    @Delete
    suspend fun delete(comentario: Comentario)
}