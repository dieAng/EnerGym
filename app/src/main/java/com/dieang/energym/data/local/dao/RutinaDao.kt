package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.domain.model.Rutina
import java.util.UUID

@Dao
interface RutinaDao {

    @Query("SELECT * FROM rutina")
    suspend fun getAll(): List<Rutina>

    @Query("SELECT * FROM rutina WHERE id = :id")
    suspend fun getById(id: UUID): Rutina?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rutina: Rutina)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rutinas: List<Rutina>)

    @Delete
    suspend fun delete(rutina: Rutina)
}