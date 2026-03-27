package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.domain.model.SesionEntrenamiento
import java.util.UUID

@Dao
interface SesionEntrenamientoDao {

    @Query("SELECT * FROM sesion_entrenamiento ORDER BY fecha DESC")
    suspend fun getAll(): List<SesionEntrenamiento>

    @Query("SELECT * FROM sesion_entrenamiento WHERE id = :id")
    suspend fun getById(id: UUID): SesionEntrenamiento?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sesion: SesionEntrenamiento)

    @Delete
    suspend fun delete(sesion: SesionEntrenamiento)
}