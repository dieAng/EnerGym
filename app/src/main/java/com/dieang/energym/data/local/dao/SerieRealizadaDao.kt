package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.data.local.entity.SerieRealizadaEntity
import java.util.UUID

@Dao
interface SerieRealizadaDao {

    @Query("SELECT * FROM serie_realizada WHERE sesionId = :sesionId")
    suspend fun getBySesion(sesionId: UUID): List<SerieRealizadaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serie: SerieRealizadaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(series: List<SerieRealizadaEntity>)

    @Delete
    suspend fun delete(serie: SerieRealizadaEntity)
}