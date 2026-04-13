package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.data.local.entity.SesionEntrenamientoEntity
import java.util.UUID

@Dao
interface SesionEntrenamientoDao {

    @Query("SELECT * FROM sesion_entrenamiento ORDER BY fecha DESC")
    suspend fun getAll(): List<SesionEntrenamientoEntity>

    @Query("SELECT * FROM sesion_entrenamiento WHERE id = :id")
    suspend fun getById(id: UUID): SesionEntrenamientoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sesionEntrenamiento: SesionEntrenamientoEntity)

    @Delete
    suspend fun delete(sesionEntrenamiento: SesionEntrenamientoEntity)
}