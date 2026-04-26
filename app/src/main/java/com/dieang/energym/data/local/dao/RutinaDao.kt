package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.data.local.entity.RutinaEntity
import java.util.UUID

@Dao
interface RutinaDao {

    @Query("SELECT * FROM rutina")
    suspend fun getAll(): List<RutinaEntity>

    @Query("SELECT * FROM rutina WHERE id = :id")
    suspend fun getById(id: UUID): RutinaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rutina: RutinaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rutinas: List<RutinaEntity>)

    @Delete
    suspend fun delete(rutina: RutinaEntity)
}