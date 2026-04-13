package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dieang.energym.data.local.entity.EjercicioEntity
import java.util.UUID

@Dao
interface EjercicioDao {

    @Query("SELECT * FROM ejercicio")
    suspend fun getAll(): List<EjercicioEntity>

    @Query("SELECT * FROM ejercicio WHERE id = :id")
    suspend fun getById(id: UUID): EjercicioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ejercicio: EjercicioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ejercicios: List<EjercicioEntity>)

    @Update
    suspend fun update(ejercicio: EjercicioEntity)

    @Delete
    suspend fun delete(ejercicio: EjercicioEntity)
}