package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dieang.energym.domain.model.RutinaEjercicio
import java.util.UUID

@Dao
interface RutinaEjercicioDao {

    @Query("SELECT * FROM rutina_ejercicio WHERE rutinaId = :rutinaId ORDER BY orden ASC")
    suspend fun getByRutina(rutinaId: UUID): List<RutinaEjercicio>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rutinaEjercicio: RutinaEjercicio)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<RutinaEjercicio>)

    @Update
    suspend fun update(rutinaEjercicio: RutinaEjercicio)

    @Delete
    suspend fun delete(rutinaEjercicio: RutinaEjercicio)
}