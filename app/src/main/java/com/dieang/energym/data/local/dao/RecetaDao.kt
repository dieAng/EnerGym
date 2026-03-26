package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dieang.energym.domain.model.Receta
import java.util.UUID

@Dao
interface RecetaDao {

    @Query("SELECT * FROM receta")
    suspend fun getAll(): List<Receta>

    @Query("SELECT * FROM receta WHERE id = :id")
    suspend fun getById(id: UUID): Receta?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(receta: Receta)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recetas: List<Receta>)

    @Update
    suspend fun update(receta: Receta)

    @Delete
    suspend fun delete(receta: Receta)
}