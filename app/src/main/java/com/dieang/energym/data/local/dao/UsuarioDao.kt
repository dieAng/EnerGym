package com.dieang.energym.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dieang.energym.data.local.entity.UsuarioEntity
import com.dieang.energym.domain.model.Usuario
import java.util.UUID

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuario")
    suspend fun getAll(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE id = :id")
    suspend fun getById(id: UUID): Usuario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: UsuarioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(usuarios: List<UsuarioEntity>)

    @Update
    suspend fun update(usuario: Usuario)

    @Delete
    suspend fun delete(usuario: UUID)
}