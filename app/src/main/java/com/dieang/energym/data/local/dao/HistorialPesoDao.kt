package com.dieang.energym.data.local.dao

import androidx.room.*
import com.dieang.energym.data.local.entity.HistorialPesoEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface HistorialPesoDao {
    @Query("SELECT * FROM historial_peso WHERE usuarioId = :usuarioId ORDER BY fecha ASC")
    fun getHistorialByUsuario(usuarioId: UUID): Flow<List<HistorialPesoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeso(peso: HistorialPesoEntity)

    @Query("DELETE FROM historial_peso WHERE id = :id")
    suspend fun deletePeso(id: Long)
}
