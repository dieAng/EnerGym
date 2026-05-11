package com.dieang.energym.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dieang.energym.data.local.EnerGymDatabase
import com.dieang.energym.data.local.entity.RutinaEntity
import com.dieang.energym.data.local.entity.UsuarioEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class RutinaDaoTest {

    private lateinit var db: EnerGymDatabase
    private lateinit var rutinaDao: RutinaDao
    private lateinit var usuarioDao: UsuarioDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, EnerGymDatabase::class.java).build()
        rutinaDao = db.rutinaDao()
        usuarioDao = db.usuarioDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetRutina() = runBlocking {
        // Given a user (required for foreign key)
        val userId = UUID.randomUUID()
        val usuario = UsuarioEntity(id = userId, nombre = "Test User", email = "test@test.com", passwordHash = "hash", edad = 25, peso = 70f, altura = 175f, objetivo = "Fuerza", fotoUrl = null)
        usuarioDao.insert(usuario)

        val rutinaId = UUID.randomUUID()
        val rutina = RutinaEntity(
            id = rutinaId,
            usuarioId = userId,
            nombre = "Pierna",
            descripcion = "Dia de pierna",
            nivel = "Avanzado",
            objetivo = "Hipertrofia"
        )

        // When
        rutinaDao.insert(rutina)
        val result = rutinaDao.getById(rutinaId)

        // Then
        assertNotNull(result)
        assertEquals(rutina.nombre, result?.nombre)
    }

    @Test
    fun getAllRutinas() = runBlocking {
        val userId = UUID.randomUUID()
        usuarioDao.insert(UsuarioEntity(id = userId, nombre = "User", email = "email@test.com", passwordHash = "p", edad = 20, peso = 60f, altura = 160f, objetivo = "Salud", fotoUrl = null))

        val r1 = RutinaEntity(usuarioId = userId, nombre = "R1", descripcion = null, nivel = null, objetivo = null)
        val r2 = RutinaEntity(usuarioId = userId, nombre = "R2", descripcion = null, nivel = null, objetivo = null)
        
        rutinaDao.insert(r1)
        rutinaDao.insert(r2)

        val list = rutinaDao.getAll()
        assertEquals(2, list.size)
    }
}
