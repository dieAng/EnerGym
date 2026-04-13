package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.data.local.entity.IngredienteEntity
import java.util.UUID

@Dao
interface IngredienteDao {

    @Query("SELECT * FROM ingrediente WHERE recetaId = :recetaId")
    suspend fun getByReceta(recetaId: UUID): List<IngredienteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingrediente: IngredienteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ingredientes: List<IngredienteEntity>)

    @Delete
    suspend fun delete(ingrediente: IngredienteEntity)
}
