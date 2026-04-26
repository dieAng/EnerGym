package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dieang.energym.data.local.entity.MensajeEntity
import java.util.UUID

@Dao
interface MensajeDao {

    @Query("""
        SELECT * FROM mensaje 
        WHERE (emisorId = :u1 AND receptorId = :u2)
        OR (emisorId = :u2 AND receptorId = :u1)
        ORDER BY fecha ASC
    """)
    suspend fun getConversacion(u1: UUID, u2: UUID): List<MensajeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(series: List<MensajeEntity>)

    @Query("SELECT * FROM mensaje WHERE sincronizado = 0")
    suspend fun getNoSincronizados(): List<MensajeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mensaje: MensajeEntity)
}