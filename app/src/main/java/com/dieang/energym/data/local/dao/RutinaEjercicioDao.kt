package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dieang.energym.data.local.entity.RutinaEjercicioEntity
import java.util.UUID

@Dao
interface RutinaEjercicioDao {

    @Query("SELECT * FROM rutina_ejercicio WHERE rutinaId = :rutinaId ORDER BY orden ASC")
    suspend fun getByRutina(rutinaId: UUID): List<RutinaEjercicioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rutinaEjercicio: RutinaEjercicioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<RutinaEjercicioEntity>)

    @Update
    suspend fun update(rutinaEjercicio: RutinaEjercicioEntity)

    @Delete
    suspend fun delete(rutinaEjercicio: RutinaEjercicioEntity)
}