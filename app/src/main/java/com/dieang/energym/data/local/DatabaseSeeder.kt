package com.dieang.energym.data.local

import com.dieang.energym.data.local.dao.*
import com.dieang.energym.data.local.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseSeeder @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val rutinaDao: RutinaDao,
    private val ejercicioDao: EjercicioDao,
    private val rutinaEjercicioDao: RutinaEjercicioDao,
    private val sesionEntrenamientoDao: SesionEntrenamientoDao
) {
    suspend fun seed() = withContext(Dispatchers.IO) {
        // 1. Crear Usuario si no existe
        val usuarios = usuarioDao.getAll()
        val usuarioId = if (usuarios.isEmpty()) {
            val newUser = UsuarioEntity(
                id = UUID.randomUUID(),
                nombre = "Usuario de Prueba",
                email = "test@energym.com",
                passwordHash = "password123",
                edad = 25,
                peso = 75f,
                altura = 180f,
                objetivo = "Fuerza",
                fotoUrl = null
            )
            usuarioDao.insert(newUser)
            newUser.id
        } else {
            usuarios.first().id
        }

        // 2. Crear Ejercicios
        val ejercicios = listOf(
            EjercicioEntity(nombre = "Press de Banca", grupoMuscular = "Pecho", equipo = "Barra", descripcion = "Press de banca tradicional", imagenUrl = null, videoUrl = null),
            EjercicioEntity(nombre = "Sentadilla", grupoMuscular = "Pierna", equipo = "Barra", descripcion = "Sentadilla libre", imagenUrl = null, videoUrl = null),
            EjercicioEntity(nombre = "Remo con Barra", grupoMuscular = "Espalda", equipo = "Barra", descripcion = "Remo inclinado", imagenUrl = null, videoUrl = null),
            EjercicioEntity(nombre = "Press Militar", grupoMuscular = "Hombro", equipo = "Mancuernas", descripcion = "Press de hombros sentado", imagenUrl = null, videoUrl = null)
        )
        ejercicioDao.insertAll(ejercicios)

        // 3. Crear Rutinas
        val rutinaFuerzaId = UUID.randomUUID()
        val rutinaFuerza = RutinaEntity(
            id = rutinaFuerzaId,
            usuarioId = usuarioId,
            nombre = "Fuerza de Cuerpo Completo",
            descripcion = "Rutina para ganar fuerza general",
            nivel = "Intermedio",
            objetivo = "Fuerza"
        )
        rutinaDao.insert(rutinaFuerza)

        // 4. Asignar Ejercicios a Rutinas
        val allEjercicios = ejercicioDao.getAll()
        allEjercicios.take(3).forEachIndexed { index, ejercicio ->
            rutinaEjercicioDao.insert(
                RutinaEjercicioEntity(
                    rutinaId = rutinaFuerzaId,
                    ejercicioId = ejercicio.id,
                    series = 4,
                    repeticiones = 8,
                    pesoObjetivo = 60f,
                    descansoSeg = 90,
                    orden = index
                )
            )
        }

        // 5. Crear Sesiones Pasadas (Inventadas)
        val hoy = System.currentTimeMillis()
        val ayer = hoy - (24 * 60 * 60 * 1000)
        val anteayer = hoy - (48 * 60 * 60 * 1000)

        val sesiones = listOf(
            SesionEntrenamientoEntity(
                id = UUID.randomUUID(),
                usuarioId = usuarioId,
                rutinaId = rutinaFuerzaId,
                fecha = anteayer,
                duracionSegundos = 3600,
                energiaGeneradaWh = 45,
                caloriasQuemadas = 400
            ),
            SesionEntrenamientoEntity(
                id = UUID.randomUUID(),
                usuarioId = usuarioId,
                rutinaId = rutinaFuerzaId,
                fecha = ayer,
                duracionSegundos = 4200,
                energiaGeneradaWh = 52,
                caloriasQuemadas = 450
            )
        )
        sesionEntrenamientoDao.insertAll(sesiones)
    }
}
