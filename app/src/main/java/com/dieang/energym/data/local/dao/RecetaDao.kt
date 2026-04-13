package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dieang.energym.data.local.entity.RecetaEntity
import java.util.UUID

@Dao
interface RecetaDao {

    @Query("SELECT * FROM receta")
    suspend fun getAll(): List<RecetaEntity>

    @Query("SELECT * FROM receta WHERE id = :id")
    suspend fun getById(id: UUID): RecetaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(receta: RecetaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recetas: List<RecetaEntity>)

    @Update
    suspend fun update(receta: RecetaEntity)

    @Delete
    suspend fun delete(receta: UUID)
}