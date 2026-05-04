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

    @Query("SELECT * FROM sesion_entrenamiento WHERE sincronizado = 0")
    suspend fun getNoSincronizados(): List<SesionEntrenamientoEntity>

    @Query("DELETE FROM sesion_entrenamiento WHERE sincronizado = 1 AND fecha < :timestamp")
    suspend fun deleteOldSynchronized(timestamp: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sesionEntrenamiento: SesionEntrenamientoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sesiones: List<SesionEntrenamientoEntity>)

    @Delete
    suspend fun delete(sesionEntrenamiento: SesionEntrenamientoEntity)
}