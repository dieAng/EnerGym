package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dieang.energym.data.local.entity.EjercicioEntity
import com.dieang.energym.domain.model.Ejercicio
import java.util.UUID

@Dao
interface EjercicioDao {

    @Query("SELECT * FROM ejercicio")
    suspend fun getAll(): List<Ejercicio>

    @Query("SELECT * FROM ejercicio WHERE id = :id")
    suspend fun getById(id: UUID): Ejercicio?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ejercicio: Ejercicio)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ejercicios: List<EjercicioEntity>)

    @Update
    suspend fun update(ejercicio: Ejercicio)

    @Delete
    suspend fun delete(ejercicio: Ejercicio)
}