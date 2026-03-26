package com.dieang.energym.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dieang.energym.data.local.dao.*
import com.dieang.energym.data.local.entity.*

@Database(
    entities = [
        UsuarioEntity::class,
        RecetaEntity::class,
        IngredienteEntity::class,
        RutinaEntity::class,
        EjercicioEntity::class,
        RutinaEjercicioEntity::class,
        SesionEntrenamientoEntity::class,
        SerieRealizadaEntity::class,
        MensajeEntity::class,
        PostEntity::class,
        LikePostEntity::class,
        ComentarioEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class EnerGymDatabase : RoomDatabase() {

    abstract fun comentarioDao(): ComentarioDao
    abstract fun ejercicioDao(): EjercicioDao
    abstract fun ingredienteDao(): IngredienteDao
    abstract fun likePostDao(): LikePostDao
    abstract fun mensajeDao(): MensajeDao
    abstract fun postDao(): PostDao
    abstract fun recetaDao(): RecetaDao
    abstract fun rutinaDao(): RutinaDao
    abstract fun rutinaEjercicioDao(): RutinaEjercicioDao
    abstract fun serieRealizadaDao(): SerieRealizadaDao
    abstract fun sesionEntrenamientoDao(): SesionEntrenamientoDao
    abstract fun usuarioDao(): UsuarioDao

}